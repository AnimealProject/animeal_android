plugins {
    id("AnimealPlugin")
    id("com.android.library")
}

dependencies {
    implementation(projects.feature.signup.entercode)
    implementation(projects.feature.signup.enterphone)
    implementation(projects.feature.signup.finishprofile)
    implementation(projects.feature.signup.onboarding)
    
    implementation(projects.library.navigation)

    implementation(libs.compose.ui)
}
