plugins {
    id("animeal.library")
}

animealPlugin {
    compose = true
    buildConfigGeneration = false
}

android {
    namespace = "com.epmedu.animeal.signup"
}

dependencies {
    implementation(projects.feature.signupflow.entercode)
    implementation(projects.feature.signupflow.enterphone)
    implementation(projects.feature.signupflow.finishprofile)
    implementation(projects.feature.signupflow.onboarding)

    implementation(projects.library.navigation)

    implementation(libs.compose.ui)
}