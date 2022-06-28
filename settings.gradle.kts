enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "Animeal"

include(":app")
include(":base")

include(":feature:login")
include(":feature:splash")
include(":feature:tabs")

include(":library:foundation")
include(":library:resources")
