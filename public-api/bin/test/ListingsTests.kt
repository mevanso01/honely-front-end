package com.metarhythm.honely.publicapi

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
import io.vertx.core.json.Json
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import io.vertx.junit5.VertxExtension
import io.vertx.junit5.VertxTestContext
import io.vertx.kotlin.core.json.json
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
import org.json.JSONArray
import java.io.File
import java.lang.Exception
import java.time.format.DateTimeFormatter
import kotlin.collections.LinkedHashMap


@ExtendWith(VertxExtension::class)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Listings Tests")
class ListingsTests {
  private val logger = LoggerFactory.getLogger(javaClass)

  private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
  private lateinit var requestSpecification: RequestSpecification

  private var suggestions = ArrayList<String>()

  private fun deployVerticle(vertx: Vertx, testContext: VertxTestContext) {

    vertx.deployVerticle(
      "com.metarhythm.honely.publicapi.MainVerticle",
      testContext.succeeding<String> { _ -> testContext.completeNow() })
  }

  @BeforeAll
  fun setup(vertx: Vertx, testContext: VertxTestContext) {
    requestSpecification = RequestSpecBuilder()
      .addFilters(Arrays.asList(ResponseLoggingFilter(), RequestLoggingFilter()))
      .setBaseUri("http://localhost:8081/")
      .setBasePath("/api/v1")
      .build()

    deployVerticle(vertx, testContext)
  }


  @Test
  @Order(1)
  @DisplayName("New York Suggestions")
  fun testNewYorkSuggestions() {
    val jsonPath = RestAssured.given(requestSpecification)
      .given()
      .queryParam("address", "New York")
      .get("/suggestions")
      .then()
      .assertThat()
      .statusCode(200)
      .extract()
      .jsonPath()

    jsonPath.getList<String>("rows").forEach {
      suggestions.add(it)
    }
    assertThat(suggestions.size).isEqualTo(5)
    assertThat(suggestions).contains("NEW YORK,NY")
  }


  @Test
  @Order(2)
  @DisplayName("New York Listings")
  fun testNewYorkListings() {
    val jsonPath = RestAssured.given(requestSpecification)
      .given()
      .queryParam("address", "New York")
      .queryParam("limit", "5")
      .get("/listings")
      .then()
      .assertThat()
      .statusCode(200)
      .extract()
      .jsonPath()

    assertThat(jsonPath.getList<JsonObject>("rows").size).isEqualTo(5)
  }


  @Test
  @Order(3)
  @DisplayName("Henderson, NJ listings result")
  fun testHendersonNJListings() {
    val jsonPath = RestAssured.given(requestSpecification)
      .given()
      .queryParam("address", "732 ELDRIDGES HILL RD HARRISONVILLE NJ 08039")
      .get("/listings")
      .then()
      .assertThat()
      .statusCode(200)
      .extract()
      .jsonPath()

    assertThat(jsonPath.getList<JsonObject>("rows").size).isEqualTo(1)
    assertThat(jsonPath.getString("total")).isEqualTo("1")
  }


  @Test
  @Order(4)
  @DisplayName("Test ApnFormat 01")
  fun testApnFormat01() {
      val jsonPath = RestAssured.given(requestSpecification)
        .given()
        .queryParam("fips", "36087")
        .queryParam("apn", "392403 66.39-1-4./4000")
        .get("/listing")
        .then()
        .assertThat()
        .statusCode(200)
        .extract()
        .jsonPath()

      assertThat(jsonPath.getString("address.full_address")).isEqualTo("1 MAIN ST APT 4302 NYACK NY 10960 3236")
      assertThat(jsonPath.getList<JsonObject>("schools.school").size).isGreaterThan(0)
  }

  @Test
  @Order(5)
  @DisplayName("Test ApnFormat 02")
  fun testApnFormat02() {
    val jsonPath = RestAssured.given(requestSpecification)
      .given()
      .queryParam("fips", "45087")
      .queryParam("apn", "19S4732CA 2303")
      .get("/listing")
      .then()
      .assertThat()
      .statusCode(200)
      .extract()
      .jsonPath()

    assertThat(jsonPath.getString("address.full_address")).isEqualTo("505 THOMPSON AVE NYSSA OR 97913 3718")
    assertThat(jsonPath.getList<JsonObject>("schools.school").size).isGreaterThan(0)
  }

  @Test
  @Order(6)
  @DisplayName("Test failing address")
  fun testFailingAddress() {
    val jsonPath = RestAssured.given(requestSpecification)
      .given()
      .queryParam("address", "505 THOMPSON AVE NYSSA OR 97913 3718")
      .get("/listings")
      .then()
      .assertThat()
      .statusCode(200)
      .extract()
      .jsonPath()

    assertThat(jsonPath.getString("rows[0].address.full_address")).isEqualTo("505 THOMPSON AVE NYSSA OR 97913 3718")
  }

  @Test
  @Order(7)
  @DisplayName("Test ApnFormat 03")
  fun testApnFormat03() {
    val jsonPath = RestAssured.given(requestSpecification)
      .given()
      .queryParam("fips", "34015")
      .queryParam("apn", "16  00028-0000-00003")
      .get("/listing")
      .then()
      .assertThat()
      .statusCode(200)
      .extract()
      .jsonPath()

    assertThat(jsonPath.getString("address.full_address")).isEqualTo("732 ELDRIDGES HILL RD HARRISONVILLE NJ 08039 ")
    assertThat(jsonPath.getList<JsonObject>("schools.school").size).isGreaterThan(0)
  }

  @Test
  @Order(8)
  @DisplayName("Test ApnFormat 04")
  fun testApnFormat04() {
    val jsonPath = RestAssured.given(requestSpecification)
      .given()
      .queryParam("fips", "05089")
      .queryParam("apn", "505-00012-000")
      .get("/listing")
      .then()
      .assertThat()
      .statusCode(200)
      .extract()
      .jsonPath()

    assertThat(jsonPath.getString("address.full_address")).isEqualTo("4291 OAKLAND RD OAKLAND AR 72661 9106")
//    assertThat(jsonPath.getList<JsonObject>("schools.school").size).isGreaterThan(0)
  }

  @Test
  @Order(8)
  @DisplayName("Test ApnFormat 05")
  fun testApnFormat05() {
    val jsonPath = RestAssured.given(requestSpecification)
      .given()
      .queryParam("fips", "06001")
      .queryParam("apn", "29A-1323-35")
      .get("/listing")
      .then()
      .assertThat()
      .statusCode(200)
      .extract()
      .jsonPath()

    assertThat(jsonPath.getString("address.full_address")).isEqualTo("4026 WATERHOUSE RD OAKLAND CA 94602 1841")
    assertThat(jsonPath.getList<JsonObject>("schools.school").size).isGreaterThan(0)
  }

  @Test
  @Order(9)
  @DisplayName("Test ApnFormat 06")
  fun testApnFormat06() {
    val jsonPath = RestAssured.given(requestSpecification)
      .given()
      .queryParam("fips", "47047")
      .queryParam("apn", "024087G A 00600")
      .get("/listing")
      .then()
      .assertThat()
      .statusCode(200)
      .extract()
      .jsonPath()

    assertThat(jsonPath.getString("address.full_address")).isEqualTo("30 VALLEY OAKS CV OAKLAND TN 38060 6089")
    assertThat(jsonPath.getList<JsonObject>("schools.school").size).isGreaterThan(0)
  }

  @Test
  @Order(10)
  @DisplayName("Test ApnFormat 07")
  fun testApnFormat07() {
    val jsonPath = RestAssured.given(requestSpecification)
      .given()
      .queryParam("fips", "36103")
      .queryParam("apn", "0900-254.00-02.00-050.001")
      .get("/listing")
      .then()
      .assertThat()
      .statusCode(200)
      .extract()
      .jsonPath()

    assertThat(jsonPath.getString("address.full_address")).isEqualTo("9 NORWOOD RD HAMPTON BAYS NY 11946 3604")
    assertThat(jsonPath.getList<JsonObject>("schools.school").size).isGreaterThan(0)
  }

  @Test
  @Order(11)
  @DisplayName("Test ApnFormat 08")
  fun testApnFormat08() {
    val jsonPath = RestAssured.given(requestSpecification)
      .given()
      .queryParam("fips", "25021")
      .queryParam("apn", "NORW M:00016 B:00009 L:00011")
      .get("/listing")
      .then()
      .assertThat()
      .statusCode(200)
      .extract()
      .jsonPath()

    assertThat(jsonPath.getString("address.full_address")).isEqualTo("9 STANDISH DR NORWOOD MA 02062 5313")
    assertThat(jsonPath.getList<JsonObject>("schools.school").size).isGreaterThan(0)
  }

  @Test
  @Order(12)
  @DisplayName("Test ApnFormat 09")
  fun testApnFormat09() {
    val jsonPath = RestAssured.given(requestSpecification)
      .given()
      .queryParam("fips", "36103")
      .queryParam("apn", "0200-724.00-05.00-021.006")
      .get("/listing")
      .then()
      .assertThat()
      .statusCode(200)
      .extract()
      .jsonPath()

    assertThat(jsonPath.getString("address.full_address")).isEqualTo("9 NORWOOD LN RONKONKOMA NY 11779 3317")
    assertThat(jsonPath.getList<JsonObject>("schools.school").size).isGreaterThan(0)
  }

  @Test
  @Order(13)
  @DisplayName("Test ApnFormat 10")
  fun testApnFormat10() {
    val jsonPath = RestAssured.given(requestSpecification)
      .given()
      .queryParam("fips", "36119")
      .queryParam("apn", "551800 4.-4310-4")
      .get("/listing")
      .then()
      .assertThat()
      .statusCode(200)
      .extract()
      .jsonPath()

    assertThat(jsonPath.getString("address.full_address")).isEqualTo("9 NORWOOD RD YONKERS NY 10710 1415")
    assertThat(jsonPath.getList<JsonObject>("schools.school").size).isGreaterThan(0)
  }

  @Test
  @Order(14)
  @DisplayName("Test No Address Listings")
  fun testNoAddressListings() {
    val jsonPath = RestAssured.given(requestSpecification)
      .given()
      .get("/listings")
      .then()
      .assertThat()
      .statusCode(200)
      .extract()
      .jsonPath()

    assertThat(jsonPath.getString("rows[0].full_address")).contains("MOUNTAIN VIEW")
  }

  @Test
  @Order(15)
  @DisplayName("Forecast Test")
  fun testForecast() {
    val jsonPath = RestAssured.given(requestSpecification)
      .given()
      .queryParam("fips", "36061")
      .queryParam("apn", "01384-1108")
      .get("/forecast")
      .then()
      .assertThat()
      .statusCode(200)
      .extract()
      .jsonPath()

    assertThat(jsonPath.getList<JsonObject>("property_forecast.percentage_change_forecasts").size).isGreaterThan(0)
    assertThat(jsonPath.getList<JsonObject>("neighborhood.percentage_change_forecasts").size).isGreaterThan(0)
    assertThat(jsonPath.getList<JsonObject>("moving_trends.percentage_change_forecasts").size).isGreaterThan(0)
  }

  @Test
  @Order(16)
  @DisplayName("Empty Forecast Test")
  fun testEmptyForecast() {
    val jsonPath = RestAssured.given(requestSpecification)
      .get("/forecast")
      .then()
      .assertThat()
      .statusCode(400)
      .extract()
      .jsonPath()

    assertThat(jsonPath.getString("error")).isEqualTo("Input not provided.")
  }

  @Test
  @Order(17)
  @DisplayName("Invalid Forecast Test")
  fun testInvalidForecast() {
    RestAssured.given(requestSpecification)
      .given()
      .queryParam("fips", "36061")
      .get("/forecast")
      .then()
      .assertThat()
      .statusCode(500)
      .extract()
      .jsonPath()
  }

  @Test
  @Order(18)
  @DisplayName("Invalid Forecast Test2")
  fun testInvalidForecast2() {
    RestAssured.given(requestSpecification)
      .given()
      .queryParam("apn", "01384-1108")
      .get("/forecast")
      .then()
      .assertThat()
      .statusCode(404)
      .extract()
      .jsonPath()
  }

  @Test
  @Order(19)
  @DisplayName("Mortgage Test")
  fun testForecast() {
    val jsonPath = RestAssured.given(requestSpecification)
      .given()
      .queryParam("fips", "36061")
      .queryParam("apn", "01384-1108")
      .get("/forecast")
      .then()
      .assertThat()
      .statusCode(200)
      .extract()
      .jsonPath()

    assertThat(jsonPath.getList<JsonObject>("property_forecast.percentage_change_forecasts").size).isGreaterThan(0)
    assertThat(jsonPath.getList<JsonObject>("neighborhood.percentage_change_forecasts").size).isGreaterThan(0)
    assertThat(jsonPath.getList<JsonObject>("moving_trends.percentage_change_forecasts").size).isGreaterThan(0)
  }

}
