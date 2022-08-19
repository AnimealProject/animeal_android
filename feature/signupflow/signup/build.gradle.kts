plugins {
    id("AnimealPlugin")
    id("com.android.library")
}

dependencies {
    implementation(projects.feature.signupflow.entercode)
    implementation(projects.feature.signupflow.enterphone)
    implementation(projects.feature.signupflow.finishprofile)
    implementation(projects.feature.signupflow.onboarding)

    implementation(projects.library.navigation)

    implementation(libs.compose.ui)
}