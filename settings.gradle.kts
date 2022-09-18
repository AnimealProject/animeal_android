import java.io.FileInputStream
import java.util.*

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()

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

include(":feature:home")
include(":feature:mainflow:moreflow:about")
include(":feature:mainflow:moreflow:account")
include(":feature:mainflow:moreflow:flow")
include(":feature:mainflow:moreflow:more")
include(":feature:mainflow:moreflow:help")
include(":feature:mainflow:moreflow:donate")
include(":feature:more")
include(":feature:signupflow:entercode")
include(":feature:signupflow:enterphone")
include(":feature:signupflow:finishprofile")
include(":feature:signupflow:onboarding")
include(":feature:signupflow:signup")
include(":feature:splash")
include(":feature:tabs")

include(":library:common")
include(":library:extensions")
include(":library:foundation")
include(":library:navigation")
include(":library:resources")
