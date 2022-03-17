package com.metarhythm.honely.blogservice
//tests
//get posts from directory instead of db -make optional
//delete post in file

//add post from form
//get posts based on vhost

import io.vertx.reactivex.core.AbstractVerticle
import io.vertx.reactivex.core.Vertx
import io.vertx.reactivex.ext.web.Router
import io.vertx.ext.web.handler.LoggerFormat
import io.vertx.kotlin.core.json.array
import io.vertx.kotlin.core.json.json
import io.vertx.kotlin.core.json.obj
import io.vertx.kotlin.pgclient.pgConnectOptionsOf
import io.vertx.reactivex.ext.web.RoutingContext
import io.vertx.reactivex.ext.web.handler.LoggerHandler
import org.slf4j.LoggerFactory
import io.vertx.reactivex.pgclient.PgPool
import io.vertx.reactivex.sqlclient.Row
import io.vertx.reactivex.sqlclient.RowSet
import io.vertx.reactivex.sqlclient.Tuple
import io.vertx.sqlclient.PoolOptions
import io.vertx.ext.healthchecks.Status
import java.text.Normalizer
import java.time.LocalDate
import java.util.*
import java.util.regex.Pattern
import com.google.common.base.Splitter
import com.google.common.base.Joiner
import io.reactivex.Single
import io.vertx.reactivex.ext.healthchecks.HealthCheckHandler
import io.vertx.reactivex.ext.web.handler.BodyHandler
import org.commonmark.ext.front.matter.YamlFrontMatterExtension
import org.commonmark.ext.front.matter.YamlFrontMatterVisitor
import org.commonmark.node.*
import org.commonmark.parser.Parser
import org.commonmark.renderer.html.HtmlRenderer
import org.commonmark.renderer.text.TextContentRenderer
import java.io.File
import java.security.InvalidParameterException
import java.time.format.DateTimeFormatter


class MainVerticle : AbstractVerticle() {
  private val logger = LoggerFactory.getLogger(javaClass)
  private var httpPort = 8082
  private var httpLogLevel = 0

  private var uploadsDir = ""
  private var postsDir = ""

  private lateinit var pgPool : PgPool
  private val sql = SqlQueries()
  private var extensions = arrayListOf(YamlFrontMatterExtension.create())
  private val parser = Parser.builder().extensions(extensions).build()
  private val htmlRenderer = HtmlRenderer.builder().build()
  private val textRenderer = TextContentRenderer.builder().build()
  private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

 // Called when verticle is deployed
  override fun start() {
    val env = System.getenv()
    httpPort = env.getOrDefault("HTTP_PORT", "8082").toInt()
    httpLogLevel = env.getOrDefault("HTTP_LOG_LEVEL", "1").toInt()

    uploadsDir = env.getOrDefault("UPLOADS_DIR", "uploads")
    postsDir = env.getOrDefault("POSTS_DIR", "posts")

    val dbHost = env.getOrDefault("PG_HOST", "localhost")
    val dbName = env.getOrDefault("PG_DB", "honely")
    val dbUser = env.getOrDefault("PG_USER", "meta")
    val dbPass = env.getOrDefault("PG_PASS", "meta")

    val options = pgConnectOptionsOf( host = dbHost, database = dbName, user = dbUser, password = dbPass)
    pgPool = PgPool.pool(vertx, options, PoolOptions())
    val router = Router.router(vertx)

    //zero for no http logging
    //3 for apache style logging
    when (httpLogLevel) {
      1 -> router.route().handler(LoggerHandler.create(LoggerFormat.TINY))
      2 -> router.route().handler(LoggerHandler.create(LoggerFormat.SHORT))
      3 -> router.route().handler(LoggerHandler.create(LoggerFormat.DEFAULT))
    }
    val fileSystem = vertx.fileSystem()
    fileSystem
     .rxExists(postsDir)
     .subscribe(
       { exists -> if(!exists) { fileSystem.rxMkdir(postsDir).subscribe() }},
       { logger.debug("can't create posts dir: $postsDir")})

   fileSystem
     .rxExists(uploadsDir)
     .subscribe(
       { exists -> if(!exists) { fileSystem.rxMkdir(uploadsDir).subscribe() }},
       { logger.debug("can't create posts dir: $uploadsDir")})

   pgPool.preparedQuery(sql.createPostsTable).rxExecute().subscribe()

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
     logger.info("Blog service api: {} {}", ctx.request().method(), ctx.request().path())
     ctx.next()
   }

   router.route().failureHandler(::failureHandler)
   router.route().handler(BodyHandler.create(uploadsDir))

   router.get("/posts").handler(this::getRecentPosts)
   router.get("/post/:y/:m/:d/:slug/").handler(this::getPost)
   router.post("/post").handler(this::createPost)
   router.put("/post/:id").handler(this::editPost)
   router.delete("/post/:id").handler(this::delPost)

    vertx.createHttpServer()
      .requestHandler(router)
      .rxListen(httpPort)
      .subscribe(
        { logger.info("Deployed http server on port {}", httpPort)},
        { err -> logger.info("error {}", err.message)})
  }

  override fun stop() {
    pgPool.close()
  }

  private fun createPost(context: RoutingContext) {
    logger.info("createPost")
    val uploads = context.fileUploads()

    if(uploads.size == 0){
      createPostFromParams(context)
    } else {
      uploads.forEach { file ->
        val uploadedFile = File(file.uploadedFileName())
        val src:String = uploadedFile.readText()
        val visitor = YamlFrontMatterVisitor()
        val document: Node = parser.parse(src)
        document.accept(visitor)
        //todo determine reasonable defaults
        //add author, location, tags
        val title = visitor.data["title"]?.first()
        val subtitle = visitor.data["subtitle"]?.firstOrNull()
        val postDate = visitor.data["date"]?.first()
        val imageFilename = visitor.data["image_filename"]?.firstOrNull()
        val slug = title?.let { makeSlug(it) }
        val body = src

        validateBlogPostParams(context, title, postDate, body)
          .flatMap { createOrUpdatePostFile(context, postDate, body, slug) }
          .subscribe(
            {
              uploadedFile.delete()
              insertPost(context, title, subtitle, postDate, slug, body, imageFilename)
            },
            { err -> handleError(context, err) }
          )
      }
    }

  }

  private fun editPost(context: RoutingContext) {
    //TODO: should this check for and delete old .md file if exists?
    val id = context.pathParam("id").toInt()
    val title = context.queryParam("title")?.first()
    val subtitle = context.queryParam("subtitle")?.firstOrNull()
    val postDate = context.queryParam("post_date")?.first()
    val body = context.queryParam("body")?.first()
    val imageFilename = context.queryParam("image_filename")?.firstOrNull()
    val slug = title?.let { makeSlug(it) }


    validateBlogPostParams(context, title, postDate, body)
            .flatMap { createOrUpdatePostFile(context, postDate, body, slug) }
            .subscribe(
              {
                updatePost(context, id, title, postDate, slug, body!!, subtitle, imageFilename)
              },
              { err -> err.printStackTrace() ; handleError(context, err) }
            )
  }

  private fun delPost(context: RoutingContext) {
    getPostById(context)
      .subscribe(
              { rows -> deletePost(context, rows) },
              { err -> handleError(context, err)})
  }

  private fun createPostFromParams(context: RoutingContext){
    val title = context.queryParam("title")?.first()
    val subtitle = context.queryParam("subtitle")?.firstOrNull()
    val postDate = context.queryParam("post_date")?.first()
    var body = context.queryParam("body")?.first()
    val imageFilename = context.queryParam("image_filename")?.firstOrNull()

    var fullBody = "---\n"+
            "title: ${title}\n"+
            "date: ${postDate}\n"

    if(subtitle != null){
        fullBody += "subtitle: ${subtitle}\n"
    }
    if(imageFilename != null){
        fullBody += "image_filename: ${imageFilename}\n"
    }

      fullBody += "---\n\n"+
            "$body"

    body = fullBody

    val slug = title?.let { makeSlug(it) }

    validateBlogPostParams(context, title, postDate, body)
      .flatMap { createOrUpdatePostFile(context, postDate, body, slug ) }
      .subscribe(
        { insertPost(context, title, subtitle, postDate, slug, body, imageFilename) },
        { err -> err.printStackTrace(); handleError(context, err) }
      )

  }

  private fun createOrUpdatePostFile(context: RoutingContext,
                                     postDate: String?, body: String?, slug: String?): Single<RoutingContext> {
    //TODO: check for permalink in src?
    val contents = "$body"

    File("$postsDir/$postDate-$slug.md").writeText(contents)

    return Single.just(context)
  }

  private fun validateBlogPostParams(routingContext: RoutingContext,
                                     title: String?, postDate: String?, body: String?): Single<RoutingContext> {

    if(title.isNullOrBlank()){
      throw InvalidParameterException("Title is a required field")
    }
    if(postDate.isNullOrBlank()){
      throw InvalidParameterException("Post Date is a required field")
    } else {
      try {
        LocalDate.parse(postDate)
      } catch (e:Exception) {
        throw InvalidParameterException("Post Date is not valid")
      }
    }
    if(body.isNullOrBlank()){
      throw InvalidParameterException("Body is a required field")
    }

    return Single.just(routingContext)
  }

  private fun insertPost(context: RoutingContext, title: String?, subtitle: String?, postDate: String?,
                         slug: String?, body: String, imageFilename: String?) {
    val postDateObj = LocalDate.parse(postDate)
    val tuple = Tuple.of(title, subtitle, postDateObj, slug, body, imageFilename)
    pgPool
      .preparedQuery(sql.insertPost)
      .rxExecute(tuple)
      .subscribe(
        { rows -> sendSetPost(context, rows) },
        { err -> handleError(context, err)})
  }

  private fun updatePost(context: RoutingContext, id: Int,
                         title: String?, postDate: String?,
                         slug: String?, body: String,
                         subtitle: String?, imageFilename: String?) {
    val postDateObj = LocalDate.parse(postDate)
    val tuple = Tuple.of(title, subtitle, postDateObj, slug, body, imageFilename)
    tuple.addInteger(id)

    pgPool
      .preparedQuery(sql.updatePost)
      .rxExecute(tuple)
      .subscribe(
        { rows -> sendSetPost(context, rows) },
        { err -> handleError(context, err)})
  }


  private fun deletePost(context: RoutingContext, rows: RowSet<Row>) {
    if(rows.size() > 0){
      rows.forEach { row ->
        val id = row.getInteger("id")
        val title = row.getString("title")
        val postDate = row.getLocalDate("post_date").format(dateFormatter)
        val slug = title?.let { makeSlug(it) }

        File("posts/$postDate-$slug.md").delete()

        val tuple = Tuple.of(id)
        pgPool
                .preparedQuery(sql.deletePost)
                .rxExecute(tuple)
                .subscribe(
                        { sendDelPost(context) },
                        { err -> handleError(context, err)})
      }
    } else {
      handleError(context, InvalidParameterException("Post not found"))
    }


  }

  private fun getPost(context: RoutingContext) {
    val year = context.pathParam("y").toInt()
    val mon = context.pathParam("m").toInt()
    val day = context.pathParam("d").toInt()
    val slug = context.pathParam("slug")
    val postDate = LocalDate.of(year, mon, day)
    val tuple = Tuple.of(postDate, slug)
    pgPool
      .preparedQuery(sql.post)
      .rxExecute(tuple)
      .subscribe(
        { rows -> sendPost(context, rows) },
        { err -> handleError(context, err)})
  }

  private fun getPostById(context: RoutingContext): Single<RowSet<Row>> {
    val id = context.pathParam("id").toInt()
    val tuple = Tuple.of(id)
    return pgPool
            .preparedQuery(sql.getPostById)
            .rxExecute(tuple)
  }

  private fun sendDelPost(context: RoutingContext ) {
    val payload = json {
      obj(
        "success" to true
      )
    }
    context.json(payload)
  }

  private fun sendSetPost(context: RoutingContext, rows: RowSet<Row>  ) {
    if(rows.size() > 0) {
      val row = rows.first()
      val payload = json {
        obj(
          "id" to row.getInteger("id"),
          "title" to row.getString("title"),
          "post_date" to row.getLocalDate("post_date").format(dateFormatter),
          "path" to rowToPath(row),
        )
      }
      context.json(payload)
    } else {
      send404(context)
    }
  }

  private fun sendPost(context: RoutingContext, rows: RowSet<Row>  ) {
    if(rows.size() > 0) {
      val row = rows.first()
      val payload = json {
        obj(
          "id" to row.getInteger("id"),
          "title" to row.getString("title"),
          "subtitle" to row.getString("subtitle"),
          "path" to rowToPath(row),
          "body" to markdownToHtml(row.getString("body")),
          "image_filename" to row.getString("image_filename")
        )
      }
      context.json(payload)
    } else {
      send404(context)
    }
  }

  private fun getRecentPosts(context: RoutingContext) {
    var limit = 9
    var offset = 0

    if(context.queryParam("limit") != null){
        limit = context.queryParam("limit").get(0).toInt()
    }
    if(context.queryParam("offset") != null){
      offset = context.queryParam("offset").get(0).toInt()
    }

    val params = Tuple.of(limit, offset)


    var total = 0

    pgPool
        .preparedQuery(sql.totalPosts)
        .rxExecute()
        .flatMap {
            if(it.first() != null) {
                total = it.first().getInteger("count")
            }

            pgPool
                .preparedQuery(sql.posts)
                .rxExecute(params)
        }
        .subscribe(
            { rows -> sendPosts(context, rows, total) },
            { err -> handleError(context, err)})

    //getPostsFromFiles(context)
  }

//  private fun getPostsFromFiles(context: RoutingContext) {
//    val fileSystem: FileSystem = vertx.fileSystem()
//    fileSystem
//      .rxReadDir("/opt/files")
//      .map {
//        files -> files.forEach {
//        println("found file: $it")
//        fileSystem
//          .rxReadFile("$it")
//          .subscribe({ file -> println(" ${markdownToHtml(file.toString())}") })
//        }
//      }
//      .subscribe(
//        { println("success") },
//        { err -> println("can't open dir: ${err.message}")}
//      )
//  }

  private fun sendPosts(context: RoutingContext, rows: RowSet<Row>, total: Int?  ) {
    val payload = json {
      obj(
        "rows" to array(),
        "size" to rows.size(),
        "total" to total)
    }
    rows.forEach { row ->
      payload.getJsonArray("rows").add(
        //row.toJson() for db field names
        json {
          obj(
            "id" to row.getInteger("id"),
            "title" to row.getString("title"),
            "path" to rowToPath(row),
            "post_date" to row.getLocalDate("post_date").format(dateFormatter),
            "image_filename" to row.getString("image_filename"),
            "truncatedBody" to truncateText(markdownToText(row.getString("body")),200).replace("\n", " ").plus(" ..."))
        }
      )
    }
    context.json(payload)
  }

  private fun rowToPath(row: Row) : String {
    val postDate = row.getLocalDate("post_date")
    return "/${postDate.year}/${postDate.monthValue}/${postDate.dayOfMonth}/${row.getString("slug")}/"
  }


  //private fun getFrontMatterData(src: String) {
  //return visitor.data
  //}

  private fun markdownToHtml(src: String) : String {
    val document: Node = parser.parse(src)
    return htmlRenderer.render(document)
  }

  private fun markdownToText(src: String) : String {
    val document: Node = parser.parse(src)
    return textRenderer.render(document)
  }

  private val nonLatin : Pattern = Pattern.compile("[^\\w_-]")
  private val separators : Pattern = Pattern.compile("[\\s\\p{Punct}&&[^-]]")

  private fun makeSlug(input: String) : String {
    val noSeparators = separators.matcher(input).replaceAll("-")
    val normalized = Normalizer.normalize(noSeparators, Normalizer.Form.NFD)
    val slug = nonLatin.matcher(normalized).replaceAll("")
    return slug.toLowerCase(Locale.ENGLISH).replace("-{2,}","-").replace("^-|-$","")
  }

  private fun stripFrontMatter(src: String) : String {
    return src.replace("\\A---(.|\n)*?---".toRegex(), "")
  }

  private fun truncateText(text : String, maxLength : Int) : String {
    val t = text.replace("<.*?>" , " ").replace("&.*?;" , "").trim()
    if (t.length < maxLength) {
      return t
    }
    val words : List<String> = Splitter.on(" ").splitToList(t)
    val truncated = ArrayList<String>()
    var totalCount = 0
    for(word in words) {
      val wordLength = word.length
      if (totalCount + 1 + wordLength > maxLength) { // +1 because of space
        break
      }
      totalCount.inc() // space
      totalCount += wordLength

      truncated.add(word)
    }
    val truncResult = Joiner.on(" ").join(truncated)
    return truncResult
  }

  //private fun sendPayload(context: RoutingContext, payload: JsonObject) {
  //  context.response()
  //    .setStatusCode(200)
  //    .putHeader("Content-Type", "application/json")
  //    .end(payload.encode())
  //}

  private fun send404(context : RoutingContext) {
    context.response().setStatusCode(404).end()
  }

  private fun handleError(context: RoutingContext, err : Throwable) {
    logger.error("Woops", err)
    context.response().setStatusCode(500).end()
  }

  private fun failureHandler(ctx: RoutingContext) {
    logger.error("Blog Service Error {}", ctx.failure())
    val response = ctx.response()
    response.setStatusCode(500).end()
  }
}

fun main() {
  Vertx.vertx()
    .rxDeployVerticle(MainVerticle())
    .subscribe()
}


