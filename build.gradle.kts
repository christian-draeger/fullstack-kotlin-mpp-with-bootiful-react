plugins {
    id("org.springframework.boot") apply false
    kotlin("jvm") apply false
    kotlin("plugin.spring") apply false
    kotlin("plugin.jpa") apply false
    id("io.spring.dependency-management") apply false
    id("com.bmuschko.docker-spring-boot-application") apply false
    id("org.unbroken-dome.test-sets") apply false
    id("com.github.ben-manes.versions")
    id("com.adarshr.test-logger") apply false
}

allprojects {
    group = "it.skrape"
    version = "0.0.1"

    repositories {
        mavenCentral()
        jcenter()
    }

    apply(plugin = "com.adarshr.test-logger")
    val isIdea = System.getProperty("idea.version") != null
    configure<com.adarshr.gradle.testlogger.TestLoggerExtension> {
        setTheme(if (isIdea) "plain" else "mocha-parallel")
        isShowFullStackTraces = false
        slowThreshold = 1000
    }
}
