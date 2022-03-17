package com.metarhythm.honely.publicapi

import io.reactivex.Single
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import io.vertx.reactivex.core.Vertx
import io.vertx.reactivex.ext.web.RoutingContext
import io.vertx.reactivex.pgclient.PgPool
import io.vertx.reactivex.sqlclient.Row
import io.vertx.reactivex.sqlclient.Tuple
import org.slf4j.LoggerFactory
import java.util.stream.Collectors

class SavedSearches(val vertx: Vertx, val pgPool: PgPool, val sql: SqlQueries) {
  private val logger = LoggerFactory.getLogger(javaClass)


  fun handleSavedSearches(ctx: RoutingContext) {

    val methodString = ctx.request().method().toString()
    if(methodString == "GET"){
      this.getSavedSearches(ctx)
    }
    if(methodString == "POST"){
      this.addSavedSearch(ctx)
    }
    if(methodString == "DELETE"){
      this.deleteSavedSearch(ctx)
    }
  }

  private fun getSavedSearches(ctx: RoutingContext) {
    val username = ctx.user().principal().getString("sub")

    getUser(username)
      .flatMap {
        getUserSavedSearches(it.getInteger("id"))
      }
      .subscribe(
        { resp ->
          val returnObj = JsonObject().put("rows", resp)
          ctx
            .response()
            .putHeader("Content-Type", "application/json")
            .setStatusCode(200).end(returnObj.encode())
        },
        { err ->
          fail500(ctx, err)
        }
      )
  }

  private fun addSavedSearch(ctx: RoutingContext) {
    var body = ctx.getBodyAsJson()
    val username = ctx.user().principal().getString("sub")

    getUser(username)
      .flatMap { json ->
        var params = Tuple.of(json.getInteger("id"), body.getString("query"))

        return@flatMap pgPool
          .preparedQuery(sql.insertSavedSearch)
          .rxExecute(params)
      }
      .subscribe(
        { resp ->
          val row = resp.firstOrNull()

          ctx
            .response()
            .putHeader("Content-Type", "application/json")
            .setStatusCode(200).end(row?.toJson()?.encode())
        },
        { err ->
          fail500(ctx, err)
        }
      )
  }

  private fun deleteSavedSearch(ctx: RoutingContext) {
    val username = ctx.user().principal().getString("sub")

    getUser(username)
      .map { json ->
        var params = Tuple.of(json.getInteger("id"), ctx.pathParam("id").toInt())

        return@map pgPool
          .preparedQuery(sql.deleteSavedSearch)
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

  private fun getUserSavedSearches(userId: Int): Single<JsonArray> {
    val params = Tuple.of(userId)
    val query = sql.getSavedSearches

    return pgPool.preparedQuery(query).rxExecute(params)
      .map { resa ->
        var json = JsonArray()

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
