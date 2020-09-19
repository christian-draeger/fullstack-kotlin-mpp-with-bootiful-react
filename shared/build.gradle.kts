plugins {
    kotlin("multiplatform")
}

kotlin {

    sourceSets {
        @Suppress("UNUSED_VARIABLE")
        val commonMain by getting {}
    }

    jvm {
        jvm()
    }

    js {
        browser()
    }
}