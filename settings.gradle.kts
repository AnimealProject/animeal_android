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
