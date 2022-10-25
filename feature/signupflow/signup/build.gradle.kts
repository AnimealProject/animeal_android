plugins {
    id("AnimealPlugin")
    id("com.android.library")
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
    implementation(projects.library.common)

    implementation(libs.compose.ui)
}