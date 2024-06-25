plugins {
    id("AnimealPlugin")
    id("com.android.library")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("org.jetbrains.kotlin.plugin.compose")
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

    implementation(projects.library.common)
    implementation(projects.library.foundation)
    implementation(projects.library.navigation)

    implementation(projects.shared.feature.router)

    implementation(libs.compose.ui)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}