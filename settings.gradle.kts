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

include(":feature:more")
include(":feature:signup")
include(":feature:signup:entercode")
include(":feature:signup:enterphone")
include(":feature:signup:finishprofile")
include(":feature:signup:onboarding")
include(":feature:splash")
include(":feature:tabs")

include(":library:common")
include(":library:extensions")
include(":library:foundation")
include(":library:navigation")
include(":library:resources")
