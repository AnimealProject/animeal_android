plugins {
    id("AnimealPlugin")
    id("com.android.library")
}

android {
    namespace = "com.epmedu.animeal.signup"
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))

    implementation(projects.feature.signupflow.entercode)
    implementation(projects.feature.signupflow.enterphone)
    implementation(projects.feature.signupflow.finishprofile)
    implementation(projects.feature.signupflow.onboarding)

    implementation(projects.library.navigation)

    implementation(libs.compose.ui)
}