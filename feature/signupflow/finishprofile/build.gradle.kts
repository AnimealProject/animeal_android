plugins {
    id("AnimealPlugin")
    id("com.android.library")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.epmedu.animeal.signup.finishprofile"
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))

    implementation(projects.library.auth)
    implementation(projects.library.common)
    implementation(projects.library.extensions)
    implementation(projects.library.foundation)
    implementation(projects.library.navigation)
    implementation(projects.library.network)
    implementation(projects.library.resources)

    implementation(projects.shared.feature.profile)
    implementation(projects.shared.feature.networkuser)

    implementation(libs.androidx.viewmodel)

    implementation(libs.compose.material)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.preview)
    debugImplementation(libs.compose.ui.tooling)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}