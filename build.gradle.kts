import com.adarshr.gradle.testlogger.TestLoggerExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") apply false
    kotlin("jvm")
    kotlin("plugin.spring") apply false
    kotlin("plugin.jpa") apply false
    id("io.spring.dependency-management") apply false
    id("com.bmuschko.docker-spring-boot-application") apply false
    id("org.unbroken-dome.test-sets") apply false
    id("com.github.ben-manes.versions")
    id("com.adarshr.test-logger")
    id("org.jlleitschuh.gradle.ktlint")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(14))
    }
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
    configure<TestLoggerExtension> {
        setTheme(if (isIdea) "plain" else "mocha-parallel")
        isShowFullStackTraces = false
        slowThreshold = 1_000
    }

    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    ktlint {
        version.set("0.39.0")
    }
}

subprojects {
    tasks {
        withType<KotlinCompile> {
            dependsOn(ktlintFormat)
        }
        withType<Test> {
            useJUnitPlatform()
            testLogging {
                events("passed", "skipped", "failed")
            }
            systemProperties = mapOf(
                "junit.jupiter.execution.parallel.enabled" to true,
                "junit.jupiter.execution.parallel.mode.default" to "concurrent",
                "junit.jupiter.execution.parallel.mode.classes.default" to "concurrent"
            )
        }
    }
}
