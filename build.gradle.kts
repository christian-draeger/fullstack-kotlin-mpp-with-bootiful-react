plugins {
    id("org.springframework.boot") apply false
    kotlin("jvm") apply false
    kotlin("plugin.spring") apply false
    kotlin("plugin.jpa") apply false
    id("io.spring.dependency-management") apply false
    id("com.bmuschko.docker-spring-boot-application") apply false
}

allprojects {
    group = "it.skrape"
    version = "0.0.1"

    repositories {
        mavenCentral()
        jcenter()
    }
}


