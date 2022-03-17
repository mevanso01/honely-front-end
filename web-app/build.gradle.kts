import com.moowork.gradle.node.yarn.YarnTask
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.30"
}

apply(plugin = "com.moowork.node")

dependencies {
  val vertxVersion = project.extra["vertxVersion"]
  val logbackClassicVersion = project.extra["logbackClassicVersion"]

  implementation("io.vertx:vertx-rx-java2:$vertxVersion")
  implementation("io.vertx:vertx-web:$vertxVersion")
  implementation("io.vertx:vertx-lang-kotlin:$vertxVersion")
  implementation("ch.qos.logback:logback-classic:$logbackClassicVersion")

  implementation(kotlin("stdlib-jdk8"))
}

val mainVerticleName = "com.metarhythm.honely.webservice.MainVerticle"
val watchForChange = "src/**/*"
val doOnChange = "./gradlew classes"
val launcherClassName = "io.vertx.core.Launcher"

application {
  mainClassName = launcherClassName
}


tasks.register<YarnTask>("buildVueApp") {
  dependsOn("yarn_install")
  inputs.file("package.json")
  inputs.file("yarn.lock")
  args = listOf("build")
}


tasks.withType<ShadowJar> {
  dependsOn("buildVueApp")

  archiveClassifier.set("fat")
  manifest {
    attributes(mapOf("Main-Verticle" to "com.metarhythm.honely.webservice.MainVerticle"))
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
val processResources by tasks.named("processResources") {
  dependsOn("buildVueApp")
}

