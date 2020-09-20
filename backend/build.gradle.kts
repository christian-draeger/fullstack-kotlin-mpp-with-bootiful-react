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

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
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
    implementation(springBoot("actuator-autoconfigure"))
    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.7.1")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.10.0")
    implementation("io.github.microutils:kotlin-logging:1.8.3")
    implementation("org.slf4j:log4j-over-slf4j:1.7.30")

    testImplementation("org.junit.jupiter:junit-jupiter")
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

    withType<Test>().configureEach {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
        systemProperties = mapOf(
            "junit.jupiter.execution.parallel.enabled" to true,
            "junit.jupiter.execution.parallel.mode.default" to "concurrent",
            "junit.jupiter.execution.parallel.mode.classes.default" to "concurrent"
        )
        finalizedBy(jacocoTestReport)
    }

    check {
        finalizedBy(jacocoTestReport)
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
