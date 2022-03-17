package com.metarhythm.honely.blogservice

import io.reactivex.Single
import io.restassured.RestAssured
import io.restassured.builder.RequestSpecBuilder
import io.restassured.filter.log.RequestLoggingFilter
import io.restassured.filter.log.ResponseLoggingFilter
import io.restassured.path.json.JsonPath
import io.restassured.specification.FilterableRequestSpecification
import io.restassured.specification.RequestSpecification
import io.vertx.core.AsyncResult
import io.vertx.core.Handler
import io.vertx.core.Promise
import io.vertx.core.Vertx
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import io.vertx.junit5.VertxExtension
import io.vertx.junit5.VertxTestContext
import io.vertx.pgclient.PgConnectOptions
import io.vertx.reactivex.pgclient.PgPool
import io.vertx.sqlclient.PoolOptions
import io.vertx.reactivex.sqlclient.Row
import io.vertx.reactivex.sqlclient.RowSet
import io.vertx.reactivex.sqlclient.Tuple
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.extension.ExtendWith
import org.slf4j.LoggerFactory
import java.time.LocalDate
import java.util.*


import org.assertj.core.api.Assertions.assertThat
import java.io.File
import java.time.format.DateTimeFormatter


@ExtendWith(VertxExtension::class)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Blog Service Post Tests")
class PostTests {
    private val logger = LoggerFactory.getLogger(javaClass)

    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    private val sql = TestSqlQueries()

    private lateinit var pgClient: PgPool
    private lateinit var requestSpecification: RequestSpecification
    private var connectOptions = PgConnectOptions()
            .setPort(5432)
            .setHost("localhost")
            .setDatabase("honely")
            .setUser("meta")
            .setPassword("meta")

    private var poolOptions = PoolOptions()
            .setMaxSize(5)

    private var blogPostData = ArrayList<JsonObject>()

    private fun deleteAllPosts(): Single<RowSet<io.vertx.reactivex.sqlclient.Row>> {
        return pgClient
            .query(sql.deleteAllPosts)
            .rxExecute()
    }

    private fun deployVerticle(vertx: Vertx, testContext: VertxTestContext) {
//        vertx.deployVerticle("MainVerticle", testContext.succeeding<String> { _ -> testContext.completeNow() })
        testContext.completeNow()
    }

    private fun addBlogPostData(jsonPath: JsonPath){
        var jsonObject = JsonObject()
                .put("id", jsonPath.getInt("id"))
                .put("title", jsonPath.getString("title"))
                .put("post_date", jsonPath.getString("post_date"))
                .put("path", jsonPath.getString("path"))

        blogPostData.add(jsonObject)
    }

    @BeforeAll
    fun setup(vertx: Vertx, testContext: VertxTestContext) {
        requestSpecification = RequestSpecBuilder()
                .addFilters(Arrays.asList(ResponseLoggingFilter(), RequestLoggingFilter()))
                .setBaseUri("http://localhost:8082/")
//                .setBasePath("/api/v1")
                .build()

        pgClient = PgPool.pool(connectOptions, poolOptions)

        deleteAllPosts()
                .subscribe(
                    { _ -> deployVerticle(vertx, testContext) },
                    { err ->
                        testContext.failNow(err)
                    })
    }


    @Test
    @Order(1)
    @DisplayName("Verify Zero Posts")
    fun testZeroPosts() {
        val jsonPath = RestAssured.given(requestSpecification)
                .get("/posts")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .jsonPath()

        assertThat(jsonPath.getInt("size")).isEqualTo(0)
    }

    @Test
    @Order(2)
    @DisplayName("Create post via file")
    fun testCreatePostViaFile() {
        val jsonPath = RestAssured.given(requestSpecification)
                .given()
                .multiPart(File("./src/test/resources/longBody.md"))
                .post("/post")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .jsonPath()


        assertThat(jsonPath.getString("path")).isEqualTo("/2020/1/11/hello-world/")

        addBlogPostData(jsonPath)
    }

    @Test
    @Order(3)
    @DisplayName("Create post via markdown file")
    fun testCreatePostViaMarkdownFile() {
        val jsonPath = RestAssured.given(requestSpecification)
                .given()
                .multiPart(File("./src/test/resources/markdown.md"))
                .post("/post")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .jsonPath()


        assertThat(jsonPath.getString("path")).isEqualTo("/2020/1/12/hello-markdown/")

        addBlogPostData(jsonPath)
    }

    @Test
    @Order(4)
    @DisplayName("Create post with params")
    fun testCreatePostWithParams() {
        val jsonPath = RestAssured.given(requestSpecification)
                .given()
                .queryParam("title", "New Post")
                .queryParam("subtitle", "test subtitle 2")
                .queryParam("post_date", "2021-03-18")
                .queryParam("body", "Short body")
                .post("/post")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .jsonPath()

        assertThat(jsonPath.getString("path")).isEqualTo("/2021/3/18/new-post/")

        addBlogPostData(jsonPath)
    }

    @Test
    @Order(5)
    @DisplayName("Get Posts")
    fun testGetPosts() {
        val jsonPath = RestAssured.given(requestSpecification)
                .get("/posts")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .jsonPath()

        assertThat(jsonPath.getInt("size")).isEqualTo(3)

        assertThat(jsonPath.getString("rows[0].truncatedBody")).isEqualTo("Short body ...")
        assertThat(jsonPath.getString("rows[1].truncatedBody")).isEqualTo("This is an <h1> tag A piece of text * Item 1 * Item 2 ...")
        assertThat(jsonPath.getString("rows[2].truncatedBody")).isEqualTo("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis pulvinar quis augue eu semper. Phasellus massa nibh, aliquet a dui nec, malesuada lacinia nisl. In vitae dolor metus. Nunc nec orci sagittis, malesuada erat non, placerat ...")

    }


    @Test
    @Order(6)
    @DisplayName("Get Posts 1 via correct slug")
    fun testGetPostOne() {
        blogPostData.forEach{ item ->
            val jsonPath = RestAssured.given(requestSpecification)
                    .get("/post${item.getString("path")}")
                    .then()
                    .assertThat()
                    .statusCode(200)
                    .extract()
                    .jsonPath()

            assertThat(jsonPath.getInt("id")).isEqualTo(item.getString("id").toInt())
        }
    }


    @Test
    @Order(7)
    @DisplayName("Get Posts 1 via incorrect date")
    fun testIncorrectDate() {
        var jsonObject = blogPostData.get(0)

        var path = jsonObject.getString("path")
        path = path.replace("2020", "2018")


        RestAssured.given(requestSpecification)
                .get("${path}")
                .then()
                .assertThat()
                .statusCode(404);
    }


    @Test
    @Order(8)
    @DisplayName("Get Posts 1 via incorrect slug")
    fun testIncorrectSlug() {
        var jsonObject = blogPostData.get(0)

        var path = jsonObject.getString("path")
        path = path.replace("hello", "helo")


        RestAssured.given(requestSpecification)
                .get("${path}")
                .then()
                .assertThat()
                .statusCode(404);
    }


    @Test
    @Order(9)
    @DisplayName("Get Markdown post")
    fun testGetMarkdown() {
        var jsonObject = blogPostData.get(1)

        val jsonPath = RestAssured.given(requestSpecification)
                .get("/post${jsonObject.getString("path")}")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .jsonPath()

        assertThat(jsonPath.getString("body")).isEqualTo("<h1>This is an &lt;h1&gt; tag</h1>\n<p>A piece of text</p>\n<ul>\n<li>Item 1</li>\n<li>Item 2</li>\n</ul>\n")
    }



    @Test
    @Order(10)
    @DisplayName("Update post with params")
    fun testUpdatePostWithParams() {
        var jsonObject = blogPostData.get(0)

        val jsonPath = RestAssured.given(requestSpecification)
                .given()
                .queryParam("title", "New Postx")
                .queryParam("post_date", "2021-03-18")
                .queryParam("body", "foo")
                .put("/post/${jsonObject.getInteger("id")}")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .jsonPath()

        assertThat(jsonPath.getString("path")).isEqualTo("/2021/3/18/new-postx/")

        jsonObject.put("title", jsonPath.getString("title"))
        jsonObject.put("post_date", jsonPath.getString("post_date"))
        jsonObject.put("path", jsonPath.getString("path"))
    }


    @Test
    @Order(11)
    @DisplayName("Get post with new path")
    fun testGetPostWithNewPath() {
        var jsonObject = blogPostData.get(0)

        RestAssured.given(requestSpecification)
                .get("/post${jsonObject.getString("path")}")
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    @Order(12)
    @DisplayName("Delete post")
    fun testDeletePost() {
        var jsonObject = blogPostData.get(0)

        val jsonPath = RestAssured.given(requestSpecification)
                .delete("/post/${jsonObject.getInteger("id")}")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .jsonPath()

        assertThat(jsonPath.getBoolean("success")).isTrue()

        blogPostData.removeAt(0)
    }


    @Test
    @Order(13)
    @DisplayName("Get Posts After Delete")
    fun testGetPostsAfterDelete() {
        val jsonPath = RestAssured.given(requestSpecification)
                .get("/posts")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .jsonPath()

        assertThat(jsonPath.getInt("size")).isEqualTo(2)

    }

    @Test
    @Order(14)
    @DisplayName("Delete all post")
    fun testDeleteAllPosts() {
        blogPostData.forEach { item ->

            val jsonPath = RestAssured.given(requestSpecification)
                    .delete("/post/${item.getInteger("id")}")
                    .then()
                    .assertThat()
                    .statusCode(200)
                    .extract()
                    .jsonPath()

            assertThat(jsonPath.getBoolean("success")).isTrue()
        }
    }


    @Test
    @Order(15)
    @DisplayName("Get Posts After Delete All")
    fun testGetPostsAfterDeleteAll() {
        val jsonPath = RestAssured.given(requestSpecification)
                .get("/posts")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .jsonPath()

        assertThat(jsonPath.getInt("size")).isEqualTo(0)

    }
}
