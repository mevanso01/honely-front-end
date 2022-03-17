package com.metarhythm.honely.publicapi

import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.core.json.json
import io.vertx.reactivex.core.Promise
import io.vertx.reactivex.core.Vertx
import io.vertx.reactivex.core.http.Cookie
import io.vertx.reactivex.ext.web.RoutingContext
import io.vertx.reactivex.ext.web.client.HttpResponse
import io.vertx.reactivex.ext.web.client.WebClient
import io.vertx.reactivex.ext.web.codec.BodyCodec
import io.vertx.reactivex.pgclient.PgPool
import io.vertx.reactivex.sqlclient.Row
import io.vertx.reactivex.sqlclient.Tuple
import org.json.JSONArray
import org.json.XML
import org.slf4j.LoggerFactory
import java.net.URL
import java.net.URLEncoder
import java.util.*
import kotlin.collections.ArrayList

class Listings(val vertx: Vertx, val pgPool: PgPool, val sql: SqlQueries, private val webClient: WebClient, private val api: String) {
  private val logger = LoggerFactory.getLogger(javaClass)

  private var apiPort = 80
  private var greatSchoolsApiKey = ""
  private var walkscoreApiKey = ""
  private var ipstackApiKey = ""

  private var greatSchoolsFieldsToKeep = ArrayList<String>(
    Arrays.asList("name", "distance", "rating", "type", "level")
  )

  init {
    val env = System.getenv()
    greatSchoolsApiKey = env.getOrDefault("GREAT_SCHOOLS_API_KEY", "wBYZN8deOj4a5gwCEdLpj7KIlEZC6usv5sAgVIs1")
    walkscoreApiKey = env.getOrDefault("WALKSCORE_API_KEY", "4a248eb8ca74f73895826343c0ef2577")
    ipstackApiKey = env.getOrDefault("IPSTACK_API_KEY", "522bdd552e56170959ed17ed896e4318")
  }

  fun getListings(ctx: RoutingContext): Single<HttpResponse<JsonObject>> {
    val request = webClient.get(apiPort, api, "/search/listings")
      .putHeader("Content-Type", "application/json")
      .`as`(BodyCodec.jsonObject())


    ctx.queryParams().forEach {
      logger.info("query param {} : {}", it.key, it.value)
      request.addQueryParam(it.key, it.value)
    }

    logger.info("request.queryParams()", request.queryParams())
    if(!request.queryParams().contains("address")){
      val address = getAddressForIp(ctx)

      request.addQueryParam("address", address)
    }


    request.queryParams().forEach {
      logger.info("after query param {} : {}", it.key, it.value)
    }

    return request.rxSend()
  }

  fun getListing(ctx: RoutingContext): Single<HttpResponse<JsonObject>> {
    return getListingApiCall(ctx.request().getParam("apn"), ctx.request().getParam("fips"))
  }

  fun getListingForFavorite(favorite: JsonObject, promise: Promise<Void>) {
    getListingApiCall(favorite.getString("apn"), favorite.getString("fips"))
      .subscribe { resp ->
        var responseBody = JsonObject(resp.body().toString())
        favorite.mergeIn(responseBody.getJsonObject("address"))
        favorite.mergeIn(responseBody.getJsonObject("structure"))
        promise.complete()
      }
  }

  private fun getListingApiCall(apn: String, fips: String): Single<HttpResponse<JsonObject>> {
    val request = webClient.get(apiPort, api, "/search/listing")
      .putHeader("Content-Type", "application/json")
      .`as`(BodyCodec.jsonObject())

    request.addQueryParam("fips", fips)
    request.addQueryParam("apn", apn)

    return request.rxSend()
  }

  fun getSchools(resp: HttpResponse<JsonObject>): Single<HttpResponse<JsonObject>> {
    logger.info("CODE {}", resp.statusCode())
    if(resp.statusCode() == 200){
      val body = resp.body()
      val address = body.getJsonObject("address")

      logger.info("get schools ADDRESS {}", address)
      if(address != null){
        val latitude = address.getString("latitude")
        val longitude = address.getString("longitude")


        return webClient.get(443, "gs-api.greatschools.org", "/nearby-schools")
          .ssl(true)
          .addQueryParam("lat", latitude)
          .addQueryParam("lon", longitude)
          .putHeader("x-api-key", greatSchoolsApiKey)
          .`as`(BodyCodec.jsonObject())
          .rxSend()
          .flatMap {

            var schools = JsonObject(it.body().toString())

            var schoolsArray = JsonArray()

            schools.getJsonArray("schools").forEach { obj ->

              if (obj is JsonObject) {
                var newObj = JsonObject()

                greatSchoolsFieldsToKeep.forEach {
                  newObj.put(it, obj.getValue(it))
                }
                newObj.put("distanceText", "mi")

                schoolsArray.add(newObj)
              }
            }

            body.put("schools", JsonObject().put("schools", schoolsArray))
            return@flatMap Single.just(resp)
          }


      } else {
        return Single.just(resp)
      }

    } else {
      return Single.just(resp)
    }
  }

  fun getWalkscore(resp: HttpResponse<JsonObject>): HttpResponse<JsonObject> {
    logger.info("CODE {}", resp.statusCode())
    if (resp.statusCode() == 200) {
      val body = resp.body()
      val address = body.getJsonObject("address")

      logger.info("ADDRESS {}", address)
      if (address != null) {
        val fullAddress = address.getString("full_address")
        val latitude = address.getString("latitude")
        val longitude = address.getString("longitude")

        try {
          val walkscoreApi = "https://api.walkscore.com/score?format=json&address=${URLEncoder.encode(fullAddress, "UTF-8")}&lat=${latitude}&lon=${longitude}&transit=1&bike=1&wsapikey=$walkscoreApiKey"
          val jsonStringResult = URL(walkscoreApi).readText()

          body.put("walkscore", JsonObject(jsonStringResult))
        } catch (e: Exception) {
          e.printStackTrace()
          body.put("walkscore", JsonObject())
        }

        return resp
      } else {

        return resp
      }
    } else {
      return resp
    }
  }


  fun getSuggestions(ctx: RoutingContext): Single<HttpResponse<JsonObject>> {
    val request = webClient.get(apiPort, api, "/search/suggestions")
      .putHeader("Content-Type", "application/json")
      .`as`(BodyCodec.jsonObject())

    if(!ctx.queryParams().isEmpty){
      ctx.queryParams().forEach {
        request.addQueryParam(it.key, it.value)
      }
    }

    return request.rxSend()
  }

  fun getForecast(ctx: RoutingContext): Single<HttpResponse<JsonObject>> {
    val request = webClient.get(apiPort, api, "/search/forecast")
      .putHeader("Content-Type", "application/json")
      .`as`(BodyCodec.jsonObject())

    if(!ctx.queryParams().isEmpty){
      ctx.queryParams().forEach {
        logger.info("Forecast api {}: {}", it.key, it.value)
        request.addQueryParam(it.key, it.value)
      }
    }

    return request.rxSend()
  }

  fun getForecastSuggestions(ctx: RoutingContext): Single<HttpResponse<JsonObject>> {
    val request = webClient.get(apiPort, api, "/search/forecast_suggestions")
      .putHeader("Content-Type", "application/json")
      .`as`(BodyCodec.jsonObject())

    if(!ctx.queryParams().isEmpty){
      ctx.queryParams().forEach {
        request.addQueryParam(it.key, it.value)
      }
    }

    return request.rxSend()
  }

  fun getMortgageCalculator(ctx: RoutingContext): Single<HttpResponse<JsonObject>> {
    val request = webClient.get(apiPort, api, "/search/mortgage_calculator")
      .putHeader("Content-Type", "application/json")
      .`as`(BodyCodec.jsonObject())

    if(!ctx.queryParams().isEmpty){
      ctx.queryParams().forEach {
        request.addQueryParam(it.key, it.value)
      }
    }

    return request.rxSend()
  }

  fun getSponsoredListing(ctx: RoutingContext): Single<JsonObject> {
    val params = Tuple.of(ctx.request().getParam("fips"), ctx.request().getParam("apn"))
    val query = sql.getSponsoredListing

    return pgPool.preparedQuery(query).rxExecute(params)
      .map { resa ->
        var row = resa.firstOrNull()

        if(row != null) {
          row.toJson()
        } else {
          JsonObject()
        }
      }
    }

  private fun getAddressForIp(ctx: RoutingContext): String {
    var ipAddr: String = ctx.request().remoteAddress().host()

    if (ctx.request().headers().contains("X-Forwarded-For")) {
      val xForwardedFor: String = ctx.request().getHeader("X-Forwarded-For")

      if(xForwardedFor.contains(",")){
        val addressList = xForwardedFor.split(",".toRegex()).toTypedArray()

        ipAddr = addressList[0]
      } else {
        ipAddr = xForwardedFor
      }
    } else {
      ipAddr = "8.8.8.8" // google name server
    }

    logger.info("ipAddr {}", ipAddr)
    if(ipAddr == "127.0.0.1"){
      ipAddr = "8.8.8.8"
    }

    val jsonStringResult: String

    val ipstack = "http://api.ipstack.com/${ipAddr}?access_key=$ipstackApiKey"
    jsonStringResult = URL(ipstack).readText()


    val ipResult = JsonObject(jsonStringResult)
    val zip = ipResult.getString("zip")

    return "${zip}"
  }

}
