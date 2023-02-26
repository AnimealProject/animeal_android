plugins {
    id("AnimealPlugin")
    id("com.android.library")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.epmedu.animeal.signup.onboarding"
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))

    implementation(projects.library.auth)
    implementation(projects.library.extensions)
    implementation(projects.library.foundation)
    implementation(projects.library.navigation)
    implementation(projects.library.common)
    implementation(projects.library.resources)

    implementation(projects.shared.feature.profile)
    implementation(projects.shared.feature.networkuser)

    implementation(libs.compose.material)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.preview)
    debugImplementation(libs.compose.ui.tooling)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}