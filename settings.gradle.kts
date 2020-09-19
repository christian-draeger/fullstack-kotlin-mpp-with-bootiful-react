@file:Suppress("LocalVariableName")
rootProject.name = "fullstack-kotlin-mpp-with-bootiful-react"

pluginManagement {
    val kotlin_version: String by settings
    val spring_boot_version: String by settings
    val test_logger_version: String by settings
    repositories {
        gradlePluginPortal()
    }
    plugins {
        kotlin("multiplatform") version kotlin_version
        kotlin("jvm") version kotlin_version
        kotlin("js") version kotlin_version
        kotlin("plugin.spring") version kotlin_version
        kotlin("plugin.jpa") version kotlin_version
        id("io.spring.dependency-management") version "1.0.8.RELEASE"
        id("org.springframework.boot") version spring_boot_version
        id("com.bmuschko.docker-spring-boot-application") version "6.6.1"
        id("com.adarshr.test-logger") version test_logger_version
        id("com.github.ben-manes.versions") version "0.29.0"
    }
}

include(
    "frontend",
    "backend",
    "shared"
)
