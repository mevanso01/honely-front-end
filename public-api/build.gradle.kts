import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.30"
}

dependencies {
  val vertxVersion = project.extra["vertxVersion"]
  val logbackClassicVersion = project.extra["logbackClassicVersion"]
  var restAssuredVersion = project.extra["restAssuredVersion"]
  var junit5Version = project.extra["junit5Version"]
  var assertjVersion = project.extra["assertjVersion"]

  implementation("io.vertx:vertx-pg-client:$vertxVersion")
  implementation("io.vertx:vertx-rx-java2:$vertxVersion")
  implementation("io.vertx:vertx-web:$vertxVersion")
  implementation("io.vertx:vertx-lang-kotlin:$vertxVersion")
  implementation("io.vertx:vertx-web-client:$vertxVersion")
  implementation("io.vertx:vertx-auth-jwt:$vertxVersion")
  implementation("io.vertx:vertx-health-check:$vertxVersion")
  implementation("io.vertx:vertx-service-discovery:$vertxVersion")
  implementation("ch.qos.logback:logback-classic:$logbackClassicVersion")
  implementation("com.google.guava:guava:28.1-jre")
  implementation("org.commonmark:commonmark:0.17.1")
  implementation("org.commonmark:commonmark-ext-yaml-front-matter:0.17.1")
  implementation(kotlin("stdlib-jdk8"))

  implementation("org.json:json:20201115")
  implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1")

  testImplementation( "io.rest-assured:rest-assured:$restAssuredVersion")
  testImplementation("org.assertj:assertj-core:$assertjVersion")
  testImplementation("io.vertx:vertx-junit5:$vertxVersion")
  testImplementation("io.vertx:vertx-pg-client:$vertxVersion")
  testImplementation("org.junit.jupiter:junit-jupiter:$junit5Version")
}

val mainVerticleName = "com.metarhythm.honely.publicapi.MainVerticle"
val watchForChange = "src/**/*"
val doOnChange = "./gradlew classes"
val launcherClassName = "io.vertx.core.Launcher"

application {
  mainClassName = launcherClassName
}

tasks.withType<ShadowJar> {
  archiveClassifier.set("fat")
  manifest {
    attributes(mapOf("Main-Verticle" to "com.metarhythm.honely.publicapi.MainVerticle"))
  }
  mergeServiceFiles {
    include("META-INF/services/io.vertx.core.spi.VerticleFactory")
  }
}

tasks.withType<JavaExec> {
  args = listOf("run", mainVerticleName, "--redeploy=$watchForChange", "--launcher-class=$launcherClassName", "--on-redeploy=$doOnChange")
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

tasks.withType<Test> {
  useJUnitPlatform()
}
