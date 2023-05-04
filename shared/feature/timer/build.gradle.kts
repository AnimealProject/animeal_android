plugins {
    id("AnimealPlugin")
    id("com.android.library")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.epmedu.animeal.timer"
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))

    implementation(projects.library.common)

    implementation(libs.compose.material)
    implementation(libs.compose.material.dialog.core)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.preview)
    implementation(projects.shared.feature.router)
    //implementation(projects.shared.feature.feeding)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    debugImplementation(libs.compose.ui.tooling)
}