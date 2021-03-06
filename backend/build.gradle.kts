@file:Suppress("UNUSED_VARIABLE")

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot")
    kotlin("jvm")
    kotlin("plugin.spring")
    id("io.spring.dependency-management")
    id("com.bmuschko.docker-spring-boot-application")
    id("com.gorylenko.gradle-git-properties")
    jacoco
}

gitProperties {
    dateFormatTimeZone = "Europe/Berlin"
    failOnNoGitDirectory = false
}

springBoot {
    buildInfo()
}

docker {
    springBootApplication {
        baseImage.set("openjdk:8-alpine")
        maintainer.set(rootProject.name)
        images.set(setOf("${rootProject.name}:latest"))
        ports.set(setOf(8080))
        jvmArgs.set(listOf("-Dspring.profiles.active=cloud", "-Xmx2048m"))
    }
}

fun springBoot(partialModule: String) = "org.springframework.boot:spring-boot-$partialModule"

dependencies {
    implementation(project(":shared"))
    implementation(kotlin("reflect"))
    implementation(springBoot("starter-web"))
    implementation(springBoot("starter-websocket"))
    implementation(springBoot("actuator-autoconfigure"))
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.1.0")
    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.7.2")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.11.2")
    implementation("io.github.microutils:kotlin-logging:2.0.3")
    implementation("org.slf4j:log4j-over-slf4j:1.7.30")

    testImplementation("org.junit.jupiter:junit-jupiter:5.7.0")
    testImplementation("io.mockk:mockk:1.10.0")
}

tasks {

    compileJava {
        options.encoding = "UTF-8"
        dependsOn(generateGitProperties)
    }

    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
            apiVersion = "1.4"
            languageVersion = "1.4"
            freeCompilerArgs = listOf("-Xjsr305=strict", "-progressive")
        }
    }

    build {
        finalizedBy(dockerCreateDockerfile)
    }

    bootRun {
        args("--spring.profiles.active=local")
    }

    val start by creating {
        dependsOn(":frontend:browserProductionWebpack")
        finalizedBy(bootRun)
    }
}
