import com.metarhythm.honely.publicapi.SqlQueries
import io.reactivex.Single
import io.vertx.core.CompositeFuture
import io.vertx.core.Future
import io.vertx.core.json.JsonObject
import io.vertx.reactivex.core.Promise
import io.vertx.reactivex.core.Vertx
import io.vertx.reactivex.ext.web.RoutingContext
import io.vertx.reactivex.pgclient.PgPool
import io.vertx.reactivex.sqlclient.Row
import io.vertx.reactivex.sqlclient.Tuple
import org.slf4j.LoggerFactory

class Users (val vertx: Vertx, val pgPool: PgPool, val sql: SqlQueries) {
    private val logger = LoggerFactory.getLogger(javaClass)

    fun setCreatedAt(ctx: RoutingContext) {
        val username = ctx.user().principal().getString("sub")

        getUser(username)
            .map { json ->
                var params = Tuple.of(json.getInteger("id"))

                return@map pgPool
                    .preparedQuery(sql.setUserLastLoggedIn)
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

    fun fetchUser(ctx: RoutingContext) {
        val username = ctx.user().principal().getString("sub")

        getUser(username)
            .subscribe(
                { resp ->
                    ctx.response()
                        .putHeader("Content-Type", "application/json")
                        .setStatusCode(200).end(resp.encode())
                },
                { err ->
                    fail500(ctx, err)
                }
            )

    }

    fun updateUser(ctx: RoutingContext) {
        val username = ctx.user().principal().getString("sub")
        val body = ctx.bodyAsJson

        getUser(username)
            .map { json ->
                var params = Tuple.of(body.getString("first_name"),
                    body.getString("last_name"), body.getString("work_phone"),
                    body.getString("cell_phone"), body.getString("address_1"),
                    body.getString("address_1"))
                params.addValue(body.getString("city"))
                params.addValue(body.getString("state"))
                params.addValue(body.getString("zip"))
                params.addValue(json.getInteger("id"))

                return@map pgPool
                    .preparedQuery(sql.updateUser)
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
        val query = sql.getUserProfile

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


    private fun fail500(ctx: RoutingContext, err: Throwable) {
        logger.error("Woops {}", err)
        ctx.fail(500, err)
    }
}