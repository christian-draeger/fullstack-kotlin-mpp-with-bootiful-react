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

    // using a library that provides a kotlin wrapper
    implementation("com.ccfraser.muirwik:muirwik-components:0.6.0")

    // using a library that is written in typescript
    // implementation(npm(name = "react-toastify", version = "6.0.8", generateExternals = true))

    // using a npm dependency that is not providing typescript type definitions or a kotlin wrapper
    // implementation(npm(name = "react-leaflet", version = "2.7.0"))
    // implementation(npm(name = "@types/react-leaflet", version = "2.5.2"))
}

kotlin {
    js {
        browser {
            distribution {
                directory = project(":backend").buildDir.resolve("resources/main/META-INF/resources")
            }
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
