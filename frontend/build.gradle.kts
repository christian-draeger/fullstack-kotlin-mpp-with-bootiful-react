plugins {
    kotlin("js")
}

repositories {
    maven("https://dl.bintray.com/cfraser/muirwik")
}

dependencies {
    implementation(project(":shared"))
    val kotlinJsVersion = "pre.112-kotlin-1.4.0"
    implementation("org.jetbrains:kotlin-react:16.13.1-$kotlinJsVersion")
    implementation("org.jetbrains:kotlin-react-dom:16.13.1-$kotlinJsVersion")
    implementation("org.jetbrains:kotlin-styled:1.0.0-$kotlinJsVersion")
    implementation("com.ccfraser.muirwik:muirwik-components:0.6.0")
}

kotlin {
    js {
        browser {
            webpackTask {
                cssSupport.enabled = true
            }

            runTask {
                cssSupport.enabled = true
            }

            testTask {
                useKarma {
                    useChromeHeadless()
                    webpackConfig.cssSupport.enabled = true
                }
            }
        }
    }
}