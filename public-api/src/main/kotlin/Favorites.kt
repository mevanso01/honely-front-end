package com.metarhythm.honely.publicapi

import io.reactivex.Single
import io.vertx.core.CompositeFuture
import io.vertx.core.Future
import io.vertx.reactivex.core.Promise
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import io.vertx.reactivex.core.Vertx
import io.vertx.reactivex.ext.web.RoutingContext
import io.vertx.reactivex.ext.web.client.HttpResponse
import io.vertx.reactivex.pgclient.PgPool
import io.vertx.reactivex.sqlclient.Row
import io.vertx.reactivex.sqlclient.Tuple
import org.json.JSONObject
import org.slf4j.LoggerFactory
import java.util.*
import java.util.stream.Collectors
import java.util.stream.Stream
import kotlin.collections.ArrayList
import kotlin.streams.toList

class Favorites(val vertx: Vertx, val pgPool: PgPool, val sql: SqlQueries, val listings: Listings) {
  private val logger = LoggerFactory.getLogger(javaClass)


  fun handleFavorites(ctx: RoutingContext) {

    val methodString = ctx.request().method().toString()
    if(methodString == "GET"){
      this.getFavorites(ctx)
    }
    if(methodString == "POST"){
      this.addFavorite(ctx)
    }
    if(methodString == "DELETE"){
      this.deleteFavorite(ctx)
    }
  }

  private fun getFavorites(ctx: RoutingContext) {
    val username = ctx.user().principal().getString("sub")
    val fullDetails = ctx.request().getParam("fullDetails").toBoolean()

    getUser(username)
      .flatMap {
        getUserFavorites(it.getInteger("id"))
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
        { err ->
          fail500(ctx, err)
        }
      )
  }

  private fun addFavorite(ctx: RoutingContext) {
    var body = ctx.getBodyAsJson()
    val username = ctx.user().principal().getString("sub")

    getUser(username)
      .map { json ->
        var params = Tuple.of(json.getInteger("id"), body.getString("address"), body.getString("fips"), body.getString("apn"))

        return@map pgPool
          .preparedQuery(sql.insertFavorite)
          .rxExecute(params)
          .subscribe()
      }
      .subscribe(
        { resp ->
          ctx.response().setStatusCode(200).end()
        },
        { err ->
          fail500(ctx, err)
        }
      )
  }

  private fun deleteFavorite(ctx: RoutingContext) {
    var body = ctx.getBodyAsJson()
    val username = ctx.user().principal().getString("sub")

    getUser(username)
      .map { json ->
        var params = Tuple.of(json.getInteger("id"), body.getString("fips"), body.getString("apn"))

        return@map pgPool
          .preparedQuery(sql.deleteFavorite)
          .rxExecute(params)
          .subscribe()
      }
      .subscribe(
        { resp ->
          ctx.response().setStatusCode(200).end()
        },
        { err ->
          fail500(ctx, err)
        }
      )
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

  private fun getUserFavorites(userId: Int): Single<List<JsonObject>> {
    val params = Tuple.of(userId)
    val query = sql.getFavorites

    return pgPool.preparedQuery(query).rxExecute(params)
      .map { resa ->
        var json = ArrayList<JsonObject>()

        val it: Iterator<Row> = resa.iterator()
        while(it.hasNext()){
          val row = it.next()

          json.add(row.toJson())
        }
        json
      }
  }


  private fun fail500(ctx: RoutingContext, err: Throwable) {
    logger.error("Woops {}", err)
    ctx.fail(500, err)
  }

}
