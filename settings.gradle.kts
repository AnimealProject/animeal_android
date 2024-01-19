import java.io.FileInputStream
import java.util.Properties



enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }

        maven {
            url = uri("https://api.mapbox.com/downloads/v2/releases/maven")
            authentication {
                create<BasicAuthentication>("basic")
            }
            credentials {
                val prop = loadProperties("local.properties")
                val mapBoxSecretToken: String? = prop.getProperty("MAPBOX_SECRET_TOKEN")

                gradle.settingsEvaluated {
                    if (mapBoxSecretToken == null) {
                        throw GradleException("MAPBOX_SECRET_TOKEN is not defined in local.properties")
                    }
                }
                // Do not change the username below.
                // This should always be `mapbox` (not your username).
                username = "mapbox"
                // Use the secret token you stored in local.properties as the password
                password = mapBoxSecretToken
            }
        }

        // Declare the Node.js download repository
        ivy {
            name = "Node.js"
            setUrl("https://nodejs.org/dist/")
            patternLayout {
                artifact("v[revision]/[artifact](-v[revision]-[classifier]).[ext]")
            }
            metadataSources {
                artifact()
            }
            content {
                includeModule("org.nodejs", "node")
            }
        }
    }
}

fun loadProperties(path: String): Properties =
    FileInputStream(path).use { inputStream ->
        Properties().apply {
            load(inputStream)
        }
    }

rootProject.name = "Animeal"

include(":app")

include(":feature:debugmenu")

include(":feature:signupflow:entercode")
include(":feature:signupflow:enterphone")
include(":feature:signupflow:finishprofile")
include(":feature:signupflow:onboarding")
include(":feature:signupflow:signup")

include(":feature:splash")

include(":feature:tabsflow:analytics")
include(":feature:tabsflow:favourites")
include(":feature:tabsflow:home")
include(":feature:tabsflow:moreflow:about")
include(":feature:tabsflow:moreflow:account")
include(":feature:tabsflow:moreflow:donate")
include(":feature:tabsflow:moreflow:faq")
include(":feature:tabsflow:moreflow:host")
include(":feature:tabsflow:moreflow:more")
include(":feature:tabsflow:moreflow:profile")
include(":feature:tabsflow:search")
include(":feature:tabsflow:tabs")

include(":library:analytics")
include(":library:api")
include(":library:auth")
include(":library:codegen")
include(":library:common")
include(":library:extensions")
include(":library:foundation")
include(":library:geolocation")
include(":library:navigation")
include(":library:network")
include(":library:resources")
include(":library:token")

include(":shared:feature:camera")
include(":shared:feature:debugmenu")
include(":shared:feature:feeding")
include(":shared:feature:networkstorage")
include(":shared:feature:networkuser")
include(":shared:feature:permissions")
include(":shared:feature:profile")
include(":shared:feature:router")
include(":shared:feature:timer")
include(":shared:feature:users")
