@file:Suppress("LocalVariableName")
rootProject.name = "fullstack-kotlin-mpp-with-bootiful-react"

pluginManagement {
    val kotlin_version: String by settings
    val spring_boot_version: String by settings
    val spring_dependency_management_version: String by settings
    val liquibase_plugin_version: String by settings
    val docker_compose_plugin_version: String by settings
    val docker_plugin_version: String by settings
    val versions_plugin_version: String by settings
    val test_sets_plugin_version: String by settings
    val git_plugin_version: String by settings
    val test_logger_version: String by settings
    repositories {
        gradlePluginPortal()
    }
    plugins {
        kotlin("multiplatform") version kotlin_version
        kotlin("jvm") version kotlin_version
        kotlin("js") version kotlin_version
        kotlin("plugin.serialization") version kotlin_version
        kotlin("plugin.spring") version kotlin_version
        kotlin("plugin.jpa") version kotlin_version
        id("io.spring.dependency-management") version spring_dependency_management_version
        id("org.springframework.boot") version spring_boot_version
        id("org.liquibase.gradle") version liquibase_plugin_version
        id("com.avast.gradle.docker-compose") version docker_compose_plugin_version
        id("com.bmuschko.docker-spring-boot-application") version docker_plugin_version
        id("com.github.ben-manes.versions") version versions_plugin_version
        id("org.unbroken-dome.test-sets") version test_sets_plugin_version
        id("com.gorylenko.gradle-git-properties") version git_plugin_version
        id("com.adarshr.test-logger") version test_logger_version
    }
}

include(
    "frontend",
    "backend",
    "shared"
)
