package com.metarhythm.honely.publicapi

import Users
import io.reactivex.Single
import io.vertx.core.CompositeFuture
import io.vertx.core.Future
import io.vertx.core.json.JsonObject
import io.vertx.ext.auth.PubSecKeyOptions
import io.vertx.ext.auth.jwt.JWTAuthOptions
import io.vertx.ext.healthchecks.Status
import io.vertx.ext.web.handler.LoggerFormat
import io.vertx.kotlin.pgclient.pgConnectOptionsOf
import io.vertx.reactivex.core.AbstractVerticle
import io.vertx.reactivex.core.Promise
import io.vertx.reactivex.core.Vertx
import io.vertx.reactivex.core.http.Cookie
import io.vertx.reactivex.ext.auth.authorization.PermissionBasedAuthorization
import io.vertx.reactivex.ext.auth.jwt.JWTAuth
import io.vertx.reactivex.ext.auth.jwt.authorization.JWTAuthorization
import io.vertx.reactivex.ext.healthchecks.HealthCheckHandler
import io.vertx.reactivex.ext.web.Router
import io.vertx.reactivex.ext.web.RoutingContext
import io.vertx.reactivex.ext.web.client.HttpResponse
import io.vertx.reactivex.ext.web.client.WebClient
import io.vertx.reactivex.ext.web.codec.BodyCodec
import io.vertx.reactivex.ext.web.handler.BodyHandler
import io.vertx.reactivex.ext.web.handler.JWTAuthHandler
import io.vertx.reactivex.ext.web.handler.LoggerHandler
import io.vertx.reactivex.ext.web.multipart.MultipartForm
import io.vertx.reactivex.pgclient.PgPool
import io.vertx.reactivex.sqlclient.Row
import io.vertx.reactivex.sqlclient.Tuple
import io.vertx.sqlclient.PoolOptions
import org.slf4j.LoggerFactory
import java.util.*


class MainVerticle : AbstractVerticle() {
  private val logger = LoggerFactory.getLogger(javaClass)
  private var httpPort = 0
  private var httpLogLevel = 0

  private var blogpostHttpPort = 0
  private var blogpostHost = ""

  private var webappHttpPort = 0
  private var webappHost = ""

  private var emailNotificationHttpPort = 0
  private var emailNotificationHost = ""

  private var contactRecipients = ""
  private var partnerRecipients = ""

  private var nonLoggedInSearchLimit = 0

  private var flagEnableSearchLimits = true

  private var remoteApi = "api.honely.com"

  private var uploadsDir = ""

  private val prefix = "/api/v1"
  private val escapedPrefix = "\\/api\\/v1"

  private lateinit var listings: Listings
  private lateinit var favorites: Favorites
  private lateinit var savedSearches: SavedSearches
  private lateinit var users: Users

  private lateinit var webClient: WebClient

  private lateinit var jwtAuth: JWTAuth
  private lateinit var jwtHandler: JWTAuthHandler

  private lateinit var pgPool: PgPool

  private val sql = SqlQueries()

  private val cryptoHelper = CryptoHelper()


  private var authzProvider = JWTAuthorization.create("permissions")



  // Called when verticle is deployed
  override fun start() {
    val env = System.getenv()
    httpPort = env.getOrDefault("HTTP_PORT", "8081").toInt()
    httpLogLevel = env.getOrDefault("HTTP_LOG_LEVEL", "1").toInt()

    blogpostHttpPort = env.getOrDefault("BLOGPOST_HTTP_PORT", "8082").toInt()
    blogpostHost = env.getOrDefault("BLOGPOST_HOST", "localhost")

    webappHttpPort = env.getOrDefault("WEBAPP_HTTP_PORT", "8083").toInt()
    webappHost = env.getOrDefault("WEBAPP_HOST", "localhost")

    emailNotificationHttpPort = env.getOrDefault("WEBAPP_HTTP_PORT", "3001").toInt()
    emailNotificationHost = env.getOrDefault("WEBAPP_HOST", "localhost")

    contactRecipients = env.getOrDefault("CONTACT_RECIPIENTS", "t.papoulas@gmail.com, kyle@staphaus.com")
    partnerRecipients = env.getOrDefault("PARTNER_RECIPIENTS", "t.papoulas@gmail.com, kyle@staphaus.com")


    nonLoggedInSearchLimit = env.getOrDefault("NON_LOGGED_IN_SEARCH_LIMIT", "15").toInt()
    flagEnableSearchLimits = env.getOrDefault("FLAG_ENABLE_SEARCH_LIMITS", "false").toBoolean()

    uploadsDir = env.getOrDefault("UPLOADS_DIR", "uploads")

    val publicKey = cryptoHelper.publicKey()
    val privateKey = cryptoHelper.privateKey()

    jwtAuth = JWTAuth.create(
      vertx, JWTAuthOptions()
        .addPubSecKey(
          PubSecKeyOptions()
            .setAlgorithm("RS256")
            .setBuffer(publicKey)
        )
        .addPubSecKey(
          PubSecKeyOptions()
            .setAlgorithm("RS256")
            .setBuffer(privateKey)
        )
    )
    jwtHandler = JWTAuthHandler.create(jwtAuth)

    val dbHost = env.getOrDefault("PG_HOST", "localhost")
    val dbName = env.getOrDefault("PG_DB", "honely")
    val dbUser = env.getOrDefault("PG_USER", "meta")
    val dbPass = env.getOrDefault("PG_PASS", "meta")

    val options = pgConnectOptionsOf( host = dbHost, database = dbName, user = dbUser, password = dbPass)
    pgPool = PgPool.pool(vertx, options, PoolOptions())


    pgPool
      .preparedQuery(sql.createFavoritesTable)
      .rxExecute()
      .flatMap {
        pgPool.query(sql.createSavedSearchesTable).rxExecute()
      }
      .flatMap {
        pgPool.query(sql.addRealtorFields).rxExecute()
      }
      .flatMap {
        pgPool.query(sql.createSponsoredListings).rxExecute()
      }
      .flatMap {
        pgPool.query(sql.createListingImages).rxExecute()
      }
      .flatMap {
        pgPool.query(sql.createInsightsSignUps).rxExecute()
      }
      .subscribe()


    webClient = WebClient.create(vertx)

    listings = Listings(vertx, pgPool, sql, webClient, remoteApi)
    favorites = Favorites(vertx, pgPool, sql, listings)
    savedSearches = SavedSearches(vertx, pgPool, sql)
    users = Users(vertx, pgPool, sql)

    val router = Router.router(vertx)

    //zero for no http logging
    //3 for apache style logging
    when (httpLogLevel) {
      1 -> router.route().handler(LoggerHandler.create(LoggerFormat.TINY))
      2 -> router.route().handler(LoggerHandler.create(LoggerFormat.SHORT))
      3 -> router.route().handler(LoggerHandler.create(LoggerFormat.DEFAULT))
    }


    val healthCheckHandler1 = HealthCheckHandler.create(vertx)
    healthCheckHandler1.register(
      "database"
    ) { promise ->
      pgPool.getConnection { connection ->
        if (connection.failed()) {
          promise.fail(connection.cause())
        } else {
          connection.result().close()
          promise.complete(Status.OK())
        }
      }
    }

    router.get("/health*").handler(healthCheckHandler1);

    router.route().handler { ctx ->
      logger.info("Public api: {} {}", ctx.request().method(), ctx.request().path())
//      ctx.request().headers().forEach {
//        logger.info("{}: {}", it.key, it.value)
//      }
      ctx.next()
    }


    router.route().failureHandler(::failureHandler)
    router.route().handler(BodyHandler.create(uploadsDir))

    router.route().pathRegex("$prefix/post.*").handler(::blogPosts)
    router.route("$prefix/favorites").handler(jwtHandler).handler(favorites::handleFavorites)
    router.route("$prefix/saved-searches").handler(jwtHandler).handler(savedSearches::handleSavedSearches)
    router.route("$prefix/saved-searches/:id").handler(jwtHandler).handler(savedSearches::handleSavedSearches)
    router.route("$prefix/contact").handler(::handleContact)
    router.route("$prefix/partner-with-us").handler(::handlePartnerWithUs)
    router.route("$prefix/set-realtor").handler(jwtHandler).handler(::handleSetRealtor)
    router.route("$prefix/get-realtor").handler(jwtHandler).handler{
      checkPerm(it, "can-authorize-realtor")
    }.handler(::handleGetRealtor)
    router.route("$prefix/authorize-realtor").handler(jwtHandler).handler{
      checkPerm(it, "can-authorize-realtor")
    }.handler(::handleAuthorizeRealtor)
    router.route("$prefix/sponsor-listing").handler(jwtHandler).handler{
      checkPerm(it, "sponsor-property")
    }.handler(::handleSponsorProperty)

    router.route("$prefix/sponsored-listing-image/:id/:filename").handler { context ->
      var id = context.pathParam("id")
      var filename = context.pathParam("filename")
      context
        .response()
        .sendFile("$uploadsDir/sponsored_listings/$id/$filename");
    }


    router.get("$prefix/my-sponsored-listing").handler(jwtHandler).handler{
      checkPerm(it, "sponsor-property")
    }.handler(::handleGetSponsoredProperty)
    router.put("$prefix/my-sponsored-listing").handler(jwtHandler).handler{
      checkPerm(it, "sponsor-property")
    }.handler(::handleUpdateSponsoredProperty)
    router.get("$prefix/my-sponsored-listings").handler(jwtHandler).handler{
      checkPerm(it, "sponsor-property")
    }.handler(::handleGetSponsoredProperties)

    router.post("$prefix/set-last-logged-in").handler(jwtHandler).handler(users::setCreatedAt)
    router.get("$prefix/user").handler(jwtHandler).handler(users::fetchUser)
    router.put("$prefix/user").handler(jwtHandler).handler(users::updateUser)

    router.post("$prefix/insights-sign-up").handler(::handleCreateInsightsSignUp)

    router
      .route()
      .pathRegex("^$prefix/(listings|related-listings|listing|suggestions|forecast_suggestions|forecast|mortgage-calculator).*$")
      .handler(::handleListings)
      .failureHandler(::failureHandler)

    //catchall, mostly for static files
    router.route().handler { ctx ->
        webClient
          .request(ctx.request().method(), webappHttpPort, webappHost, ctx.request().path())
          .`as`(BodyCodec.buffer())
          .rxSend()
          .subscribe(
            { resp ->
              var response = ctx.response()
              for (header in resp.headers()) {
                response.putHeader(header.key, header.value)
              }

              response.end(resp.bodyAsBuffer())
            },
            { err -> sendBadGateway(ctx, err) }
          )
      }

    vertx.createHttpServer()
      .requestHandler(router)
      .rxListen(httpPort)
      .subscribe(
        { logger.info("Deployed http server on port {}", httpPort) },
        { err -> logger.info("error {}", err.message) })

  }

  override fun stop() {
    // pool.close()
  }

  private fun handleListings(ctx: RoutingContext){
    var response = when(ctx.request().path()){
      "$prefix/listings" -> listings.getListings(ctx)
      "$prefix/related-listings" -> listings.getListings(ctx)
      "$prefix/listing" -> listings.getListing(ctx)
        .flatMap { resp-> listings.getSchools(resp) }
        .map { resp ->
          listings.getWalkscore(resp)
        }
      "$prefix/suggestions" -> listings.getSuggestions(ctx)
      "$prefix/forecast" -> listings.getForecast(ctx)
      "$prefix/forecast_suggestions" -> listings.getForecastSuggestions(ctx)
      "$prefix/mortgage-calculator" -> listings.getMortgageCalculator(ctx)
      else -> null
    }

    if(response != null){
      response.subscribe(
        { resp ->
           when(ctx.request().path()) {
            "$prefix/listings" -> {
              var body = JsonObject(resp.body().toString())

              if (body.containsKey("error")) {
                if (ctx.queryParams().contains("address") && body.getString("error").contains("No entries")) {
                  ctx.queryParams().remove("address")
                  ctx.queryParams().add("no-entries", "true")

                  handleListings(ctx)
                } else {
                  forwardJsonOrStatusCode(ctx, resp)
                }
              } else {
                forwardJsonOrStatusCode(ctx, resp)
              }
            }
            "$prefix/listing" -> {
              var body = JsonObject(resp.body().toString())

              logger.info("Listing body {}", body)


              checkSearchLimit(ctx, body.getJsonObject("address").getString("property_id"))
                .subscribe(
                    { returnSearch ->
                      logger.info("CHECK SEARCH RESULT {}", returnSearch)
                      if(returnSearch) {
                        listings.getSponsoredListing(ctx)
                          .subscribe(
                            { resp ->
                              var sponsoredData = resp
                              logger.info("Sponsored {}", sponsoredData)
                              if(sponsoredData != null){
                                body.put("is_sponsored", true)
                                body.put("sponsored_listing_id", sponsoredData.getInteger("id"))
                                if(sponsoredData.getString("description") != null){
                                  body.put("description", sponsoredData.getString("description"))
                                }
                                if(sponsoredData.getJsonArray("images") != null) {
                                  body.put("images", sponsoredData.getJsonArray("images"))
                                }
                              }
                              ctx.response().setStatusCode(200).end(body.encode())
                            },
                            {
                                err -> handleError(ctx, err)
                            }
                          )
                      } else {
                        var errorMessage = JsonObject()
                          .put("errorMessage", "Search count exceeded. Please create an account to continue searching")
                          .put("address", body.getJsonObject("address").getString("full_address"));
                        ctx.response().setStatusCode(500).end(errorMessage.encode())
                      }
                    },
                { err ->
                  handleError(ctx, err)
                }
              )

            }
            "$prefix/forecast" -> {
              var body = JsonObject(resp.body().toString())

              logger.info("Forecast body {}", body)

              var checkString = body.getString("zipcode");
              if(body.getJsonObject("property_forecast") != null){
                checkString = body.getJsonObject("property_forecast").getString("property_id")
              }

              checkSearchLimit(ctx, checkString)
                .subscribe(
                  { returnSearch ->
                      logger.info("CHECK SEARCH RESULT {}", returnSearch)
                    if(returnSearch) {
                      forwardJsonOrStatusCode(ctx, resp)
                    } else {
                      var errorMessage = JsonObject().put("errorMessage", "Search count exceeded. Please create an account to continue searching");
                      ctx.response().setStatusCode(500).end(errorMessage.encode())
                    }
                  },
                  { err ->
                    handleError(ctx, err)
                  }
                )
            }

            else -> forwardJsonOrStatusCode(ctx, resp)
          }
        },
        { err -> sendBadGateway(ctx, err) }
      )
    } else {
      ctx.next()
    }
  }

  private fun blogPosts(ctx: RoutingContext) {
    logger.info("Blogposts");
    val blogPostPath = ctx.request().path().replace(prefix, "")

    val request = webClient
      .request(ctx.request().method(), blogpostHttpPort, blogpostHost, blogPostPath)
      .`as`(BodyCodec.jsonObject())

    if (!ctx.queryParams().isEmpty) {
      ctx.queryParams().forEach {
        request.addQueryParam(it.key, it.value)
      }
    }

    val uploads = ctx.fileUploads()
    logger.info("uploads.size {}",uploads.size)
    when {
      uploads.size > 0 -> {

        val form = MultipartForm.create()
        for (upload in uploads) {
          form.binaryFileUpload(
            upload.name(),
            upload.fileName(),
            upload.uploadedFileName(),
            upload.contentType(),
          )
        }

        request
          .`as`(BodyCodec.jsonObject())
          .rxSendMultipartForm(form)
          .subscribe(
            { resp -> forwardJsonOrStatusCode(ctx, resp) },
            { err -> sendBadGateway(ctx, err) }
          )
      }



      ctx.bodyAsJson != null -> {
        request
          .putHeader("Content-Type", "application/json")
          .`as`(BodyCodec.jsonObject())
          .rxSendJson(ctx.bodyAsJson)
          .subscribe(
            { resp -> forwardJsonOrStatusCode(ctx, resp) },
            { err -> sendBadGateway(ctx, err) }
          )
      }
      else -> {
        request
          .rxSend()
          .subscribe(
            { resp -> forwardJsonOrStatusCode(ctx, resp) },
            { err -> sendBadGateway(ctx, err) }
          )
      }
    }
  }


  private fun handleContact(ctx: RoutingContext){

    var body = ctx.bodyAsJson

    body.put("to", contactRecipients)
    body.put("template", "contact_us")

    webClient
      .post(
        emailNotificationHttpPort,
        emailNotificationHost,
        "/notify-by-template/"
      )
      .`as`(BodyCodec.jsonObject())
      .rxSendJson(body)
      .subscribe(
        {resp -> forwardJsonOrStatusCode(ctx, resp)},
        {err -> sendBadGateway(ctx, err)}
      )
  }

  private fun handlePartnerWithUs(ctx: RoutingContext){

    var body = ctx.bodyAsJson

    body.put("to", partnerRecipients)
    body.put("template", "partner_with_us")

    webClient
      .post(
        emailNotificationHttpPort,
        emailNotificationHost,
        "/notify-by-template/"
      )
      .`as`(BodyCodec.jsonObject())
      .rxSendJson(body)
      .subscribe(
        {resp -> forwardJsonOrStatusCode(ctx, resp)},
        {err -> sendBadGateway(ctx, err)}
      )
  }

  private fun handleSetRealtor(ctx: RoutingContext) {
    val username = ctx.user().principal().getString("sub")
    var uuid = UUID.randomUUID().toString()

    var body = ctx.getBodyAsJson()

    getUser(username)
      .map { json ->
        var params = Tuple.of(
          body.getString("realtorId"),
          "unconfirmed",
          uuid,
          username)

        return@map pgPool
          .preparedQuery(sql.setRealtorFields)
          .rxExecute(params)
          .subscribe()
      }
      .map {

        val host = ctx.request().getHeader("x-forwarded-host");
        val json = JsonObject()
          .put("subject", "New Realtor Registration")
          .put("link", "http://$host/admin/confirm-realtor/$uuid")
          .put("email", username)
          .put("template", "realtor_alert")
          .put("to", contactRecipients);

        return@map webClient
          .post(
            emailNotificationHttpPort,
            emailNotificationHost,
            "/notify-by-template/"
          )
          .`as`(BodyCodec.jsonObject())
          .rxSendJson(json)
          .subscribe()
      }
      .subscribe(
        { resp ->

          ctx.response().setStatusCode(200).end()
        },
        { err ->
          sendBadGateway(ctx, err)
        }
      )
  }

  private fun handleGetRealtor(ctx: RoutingContext) {

    var params = Tuple.of(ctx.request().getParam("uuid"))

    pgPool
      .preparedQuery(sql.getRealtorByUuid)
      .rxExecute(params)
      .subscribe(
        { rows ->
          var row = rows.firstOrNull()

          if(row == null){
            throw RuntimeException("User not found")
          } else {
            ctx.response().setStatusCode(200).end(row?.toJson()?.encode())
          }

        },
        { err -> handleError(ctx, err)})
  }

  private fun handleAuthorizeRealtor(ctx: RoutingContext) {

    var params = Tuple.of(ctx.bodyAsJson.getInteger("id"))

    logger.info("PARAMS: {}", ctx.bodyAsJson.getInteger("id"))

    pgPool
      .preparedQuery(sql.confirmRealtor)
      .rxExecute(params)
      .map {
        return@map pgPool
          .preparedQuery(sql.setRelatorRole)
          .rxExecute(params)
          .subscribe()
      }
      .subscribe(
        {
          ctx.response().setStatusCode(200).end()
        },
        { err -> handleError(ctx, err)})
  }

  private fun handleSponsorProperty(ctx: RoutingContext) {
    val username = ctx.user().principal().getString("sub")

    var params = Tuple.of(ctx.bodyAsJson.getString("fips"), ctx.bodyAsJson.getString("apn"), username)

    getUser(username)
      .map {
        return@map pgPool
          .preparedQuery(sql.insertSponsorListing)
          .rxExecute(params)
          .subscribe()
      }
      .subscribe(
        {
          ctx.response().setStatusCode(200).end()
        },
        { err -> handleError(ctx, err)})
  }

  private fun handleGetSponsoredProperties(ctx: RoutingContext) {
    val username = ctx.user().principal().getString("sub")
    val fullDetails = ctx.request().getParam("fullData").toBoolean()

    var params = Tuple.of(username)

    pgPool
      .preparedQuery(sql.getMySponsoredListings)
      .rxExecute(params)
      .map { resa ->
        var json = ArrayList<JsonObject>()

        val it: Iterator<Row> = resa.iterator()
        while(it.hasNext()){
          val row = it.next()

          json.add(row.toJson())
        }
        json
      }
      .subscribe(
        { resp ->
          var promiseList = mutableListOf<Future<Void>>()

          if(fullDetails){
            resp.forEachIndexed { index, favorite ->
              var promise = Promise.promise<Void>()
              promiseList.add(promise.future())

              listings.getListingForFavorite(favorite, promise)
            }
          } else {
            var promise = Promise.promise<Void>()
            promiseList.add(promise.future())
            promise.complete()
          }

          CompositeFuture.all(promiseList as List<Future<Any>>?).onComplete {

            val returnObj = JsonObject().put("rows", resp)
            ctx
              .response()
              .putHeader("Content-Type", "application/json")
              .setStatusCode(200).end(returnObj.encode())
          }
        },
        { err -> handleError(ctx, err)}
      )
  }

  private fun handleGetSponsoredProperty(ctx: RoutingContext) {
    val username = ctx.user().principal().getString("sub")

    listings.getSponsoredListing(ctx)
      .subscribe(
        { resp ->
          var sponsoredData = resp
          var body = JsonObject()
          if(sponsoredData == null){
            ctx.response()
              .setStatusCode(500).end("Property not found")
          } else {
            ctx.response()
              .putHeader("Content-Type", "application/json")
              .setStatusCode(200).end(sponsoredData.encode())
          }
        },
        {
            err -> handleError(ctx, err)
        }
      )

  }

  private fun handleUpdateSponsoredProperty(ctx: RoutingContext) {
    val username = ctx.user().principal().getString("sub")
    val uploads = ctx.fileUploads()
    val id = ctx.request().getParam("id").toInt()
    var userId = -1;
    val description = ctx.request().getParam("description")

    getUser(username)
      .flatMap {
        userId = it.getInteger("id")

        return@flatMap listings.getSponsoredListing(ctx)
      }
      .flatMap{ resp ->
        var sponsoredData = resp

        if(sponsoredData == null){
          throw RuntimeException("Sponsored data not found")
        } else {
          if(sponsoredData.getInteger("user_id") != userId){
            throw RuntimeException("Sponsored data not found")
          } else {
            var images = sponsoredData.getJsonArray("images")

            if(!uploads.isEmpty()){
              for (upload in uploads) {
                var location = "$uploadsDir/sponsored_listings/$id/${upload.fileName()}"
                if(vertx.fileSystem().existsBlocking(location)){
                  vertx.fileSystem().deleteBlocking(location)
                }
                var locationParts = location.split("/")
                var folderStructure = ""
                var fileName = upload.fileName()
                for (locationPart in locationParts) {
                  if(locationPart != fileName) {
                    folderStructure += locationPart + "/";

                    if(!vertx.fileSystem().existsBlocking(folderStructure)){
                      vertx.fileSystem().mkdirBlocking(folderStructure);
                    }
                  }

                }
                vertx.fileSystem().moveBlocking(upload.uploadedFileName(), "$folderStructure/$fileName")

                pgPool
                  .preparedQuery(sql.insertListingImage)
                  .rxExecute(Tuple.of(id, fileName))
                  .subscribe()
              }

              return@flatMap pgPool
                .preparedQuery(sql.updateSponsoredListingDescription)
                .rxExecute(Tuple.of(description, id))
            } else {

              logger.info("$id : $description")

              return@flatMap pgPool
                .preparedQuery(sql.updateSponsoredListingDescription)
                .rxExecute(Tuple.of(description, id))
            }


          }
        }

      }
      .subscribe(
        { resp ->
          ctx.response().setStatusCode(200).end()
        },
        {
            err -> handleError(ctx, err)
        }
      )


  }

  private fun handleCreateInsightsSignUp(ctx: RoutingContext){

    var body = ctx.bodyAsJson
    var email = body.getString("email").toLowerCase().trim()


    var params = Tuple.of(email)

    pgPool
      .preparedQuery(sql.insertInsightsSignUp)
      .rxExecute(params)
      .subscribe(
        { resp ->
          ctx.response().setStatusCode(200).end()
        },
        { err -> handleError(ctx, err)}
      )
  }


  private fun forwardJsonOrStatusCode(ctx: RoutingContext, resp: HttpResponse<JsonObject>) {
//    logger.info("statusCode: {}", resp.statusCode())
//    logger.info("body: {}", resp.body())

    var body = JsonObject(resp.body().toString())

    when(ctx.request().path()) {
      "$prefix/listings" -> {
        if(ctx.queryParams().contains("no-entries")){
          body.put("noAddressEntries", true)
        }
      }
    }

    ctx.response()
      .setStatusCode(resp.statusCode())
      .putHeader("Content-Type", "application/json")
      .end(body.encode())
  }

  private fun sendStatusCode(ctx: RoutingContext, code: Int) {
    ctx.response().setStatusCode(code).end()
  }

  private fun sendBadGateway(ctx: RoutingContext, err: Throwable) {
    logger.error("Woops", err)
    logger.error("{}", ctx.request().path())
    logger.error("{}", ctx.statusCode())
    logger.error("{}", ctx.failure())
    ctx.fail(502)
  }

  private fun send404(context: RoutingContext) {
    context.response().setStatusCode(404).end()
  }

  private fun handleError(context: RoutingContext, err: Throwable) {
    logger.error("Woops", err)
    context.response().setStatusCode(500).end()
  }

  private fun failureHandler(ctx: RoutingContext) {
    logger.error("Public Api Error {}", ctx.failure())
    ctx.failure().printStackTrace()
    val response = ctx.response()
    response.setStatusCode(500).end(ctx.failure().message)
  }

  private fun getUser(username: String): Single<JsonObject> {
    val params = Tuple.of(username)
    val query = sql.getUser

    return pgPool.preparedQuery(query).rxExecute(params)
      .map { resa ->
        var json = JsonObject()

        json = if (resa.rowCount() == 1) {
          val it: Iterator<Row> = resa.iterator()
          val row = it.next()
          row.toJson()
        } else {
          throw RuntimeException("User not found")
        }

        return@map json
      }
  }

  private fun checkPerm(ctx: RoutingContext, perm: String){
    authzProvider.getAuthorizations(ctx.user()) { allowed ->
      if (PermissionBasedAuthorization.create(perm).match(ctx.user())) {
        ctx.next()
      } else {
        ctx.response().setStatusCode(403).end()
      }
    }
  }

  private fun checkSearchLimit(ctx: RoutingContext, checkString: String): Single<Boolean> {
    return jwtHandler.rxParseCredentials(ctx)
      .map { result ->
        logger.info("REUSLT {}", result.toJson())
        if(result.toJson().getString("token") == "null"){
          var searchCookie = ctx.getCookie("searches")
          logger.info("searchcookie {}", searchCookie)
          if(searchCookie == null){
            searchCookie = Cookie.cookie("searches", "")
          }
          logger.info("checkstring {}", checkString)
          logger.info("search cookie value {}", searchCookie.value)
          val searches = searchCookie.value.splitToSequence(":")
            .filter { it.isNotEmpty() } // or: .filter { it.isNotBlank() }
            .toMutableList()

          logger.info("SIZE {} : Contains {}", searches.size, searches.contains(checkString))
          var returnSearch = searches.size < nonLoggedInSearchLimit || searches.contains(checkString)

          if(!searches.contains(checkString) && searches.size < nonLoggedInSearchLimit){
            searches.add(checkString)
          }
          logger.info("new cookie {}", searches.joinToString(":"))
          searchCookie.value = searches.joinToString(":")
          ctx.addCookie(searchCookie)

          return@map returnSearch
        } else {
          return@map true
        }
      }

  }
}

fun main() {
  Vertx.vertx()
    .rxDeployVerticle(MainVerticle())
    .subscribe()
}