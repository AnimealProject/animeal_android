import java.util.Properties

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
                // Do not change the username below.
                // This should always be `mapbox` (not your username).
                username = "mapbox"
                // Use the secret token you stored in secrets.properties as the password
                password = getSecretToken()
            }
        }
    }
}

fun getSecretToken(): String {
    val propertiesFile = file("secrets.properties")
    if (propertiesFile.exists()) {
        val properties = Properties()
        properties.load(propertiesFile.inputStream())
        return properties.getProperty("MAPBOX_ACCESS_TOKEN")
            ?: throw BuildCancelledException("MAPBOX_ACCESS_TOKEN is not defined")
    }
    throw BuildCancelledException("secrets.properties is not exist")
}


rootProject.name = "Animeal"

include(":app")

include(":feature:login")
include(":feature:more")
include(":feature:splash")
include(":feature:tabs")
include(":feature:home")

include(":library:common")
include(":library:extensions")
include(":library:foundation")
include(":library:navigation")
include(":library:resources")
