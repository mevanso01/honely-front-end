package com.metarhythm.honely.webservice

import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise
import io.vertx.core.Vertx
import io.vertx.core.http.HttpMethod
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.CorsHandler
import io.vertx.ext.web.handler.LoggerFormat
import io.vertx.ext.web.handler.LoggerHandler
import io.vertx.ext.web.handler.StaticHandler
import org.slf4j.LoggerFactory
import java.util.HashSet

class MainVerticle : AbstractVerticle() {
  private val logger = LoggerFactory.getLogger(javaClass)
  private var httpPort = 8083
  private var httpLogLevel = 1

  override fun start(startPromise: Promise<Void>) {
    val env = System.getenv()
    httpPort = env.getOrDefault("HTTP_PORT", "8083").toInt()
    httpLogLevel = env.getOrDefault("HTTP_LOG_LEVEL", "1").toInt()

    val router = Router.router(vertx)

    val allowedMethods: MutableSet<HttpMethod> = HashSet()
    allowedMethods.add(HttpMethod.GET)
    allowedMethods.add(HttpMethod.POST)
    allowedMethods.add(HttpMethod.PUT)

    //set to zero for no http logging
    //3 is apache style logging
    when (httpLogLevel) {
      1 -> router.route().handler(LoggerHandler.create(LoggerFormat.TINY))
      2 -> router.route().handler(LoggerHandler.create(LoggerFormat.SHORT))
      3 -> router.route().handler(LoggerHandler.create(LoggerFormat.DEFAULT))
    }

    router.route().handler(CorsHandler.create("*").allowedMethods(allowedMethods))


    router.route().handler { ctx ->
      logger.info("Path: {}", ctx.request().path())
      ctx.next()
    }

    router.route().failureHandler(::failureHandler)
    router.route().handler(StaticHandler.create())

    //This is a catch all
    router.route()
      .handler { ctx ->
        logger.info("I have no static file and no route handler: {}", ctx.request().path())

        if (vertx.fileSystem().existsBlocking("webroot/app.html")) {
          ctx.reroute("/app.html")
        } else {
          ctx.next()
        }
      }


    vertx.createHttpServer()
      .requestHandler(router)
      .listen(httpPort) { http ->
        if (http.succeeded()) {
          startPromise.complete()
          println("HTTP server started on port $httpPort")
        } else {
          startPromise.fail(http.cause())
          println("HTTP server failed")
        }
      }
  }

  private fun failureHandler(ctx: RoutingContext) {
    logger.error("Blog Service Error {}", ctx.failure())
    val response = ctx.response()
    response.setStatusCode(500).end()
  }
}

fun main() {
  Vertx.vertx().deployVerticle(MainVerticle())
}
