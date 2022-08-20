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
                val MAPBOX_SECRET_TOKEN: String? by settings
                gradle.settingsEvaluated {
                    if(MAPBOX_SECRET_TOKEN == null)
                    throw GradleException("MAPBOX_SECRET_TOKEN is not defined in gradle.properties")
                }
                // Do not change the username below.
                // This should always be `mapbox` (not your username).
                username = "mapbox"
                // Use the secret token you stored in secrets.properties as the password
                password = MAPBOX_SECRET_TOKEN
            }
        }
    }
}

rootProject.name = "Animeal"

include(":app")

include(":feature:more")
include(":feature:signupflow:entercode")
include(":feature:signupflow:enterphone")
include(":feature:signupflow:finishprofile")
include(":feature:signupflow:onboarding")
include(":feature:signupflow:signup")
include(":feature:splash")
include(":feature:tabs")
include(":feature:home")

include(":library:common")
include(":library:extensions")
include(":library:foundation")
include(":library:navigation")
include(":library:resources")
