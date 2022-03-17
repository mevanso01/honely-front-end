plugins {
    id("com.google.cloud.tools.jib") version "2.5.0" apply false
    id("com.github.johnrengelman.shadow") version "5.2.0" apply false
    id("com.adarshr.test-logger") version "2.0.0" apply false
    id("com.moowork.node") version "1.3.1" apply false
    id("org.gradle.test-retry") version "1.1.4" apply false
}

allprojects {
    extra["vertxVersion"] = if (project.hasProperty("vertxVersion")) project.property("vertxVersion") else "4.0.2"
    extra["junit5Version"] = "5.7.0"
    extra["restAssuredVersion"] = "4.3.1"
    extra["logbackClassicVersion"] = "1.2.3"
    extra["assertjVersion"] = "3.15.0"
    extra["testContainersVersion"] = "1.13.0"
}


subprojects {
    repositories {
      jcenter()
      mavenCentral()
      maven("https://dl.bintray.com/jetbrains/markdown/")
      maven("https://oss.sonatype.org/content/repositories/snapshots")
    }

    apply(plugin = "java")
    apply(plugin="com.google.cloud.tools.jib")
    apply(plugin = "application")
    apply(plugin = "com.github.johnrengelman.shadow")
    apply(plugin = "com.adarshr.test-logger")

    tasks.withType<JavaCompile> {
        sourceCompatibility = "1.8"
        targetCompatibility = "1.8"
    }

    tasks.named<Test>("test") {
        reports.html.isEnabled = false
    }
}

tasks.wrapper {
    distributionType = Wrapper.DistributionType.ALL
    gradleVersion = "6.6.1"
}
