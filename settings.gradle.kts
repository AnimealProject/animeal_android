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
include(":feature:more")
include(":feature:splash")
include(":feature:tabs")
include(":feature:home")

include(":library:common")
include(":library:extensions")
include(":library:foundation")
include(":library:navigation")
include(":library:resources")
