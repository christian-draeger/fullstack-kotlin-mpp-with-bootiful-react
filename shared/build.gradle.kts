@file:Suppress("PropertyName", "SuspiciousCollectionReassignment")

import org.jetbrains.kotlin.gradle.dsl.KotlinCompile
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile

@Suppress("VariableNaming")
val kotlin_serialization_version: String by project

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
}

kotlin {

    sourceSets {
        commonMain {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlin_serialization_version")
            }
        }
    }

    jvm {
        jvm()
    }

    js(BOTH) {
        browser()
    }
}

tasks {
    withType<KotlinCompile<*>>().all {
        kotlinOptions {
            freeCompilerArgs += listOf("-Xopt-in=kotlin.ExperimentalUnsignedTypes")
            freeCompilerArgs += listOf("-Xopt-in=kotlinx.serialization.ExperimentalSerializationApi")
        }
    }

    withType<KotlinJvmCompile>().configureEach {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
}
