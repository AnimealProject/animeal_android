plugins {
    id("animeal.library")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

animealPlugin {
    compose = true
    buildConfigGeneration = false
}

android {
    namespace = "com.epmedu.animeal.signup.finishprofile"
}

dependencies {
    implementation(projects.library.common)
    implementation(projects.library.extensions)
    implementation(projects.library.foundation)
    implementation(projects.library.navigation)
    implementation(projects.library.resources)

    implementation(projects.shared.feature.profile)

    implementation(libs.androidx.viewmodel)

    implementation(libs.compose.material)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.preview)
    debugImplementation(libs.compose.ui.tooling)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}