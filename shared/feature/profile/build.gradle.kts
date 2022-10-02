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
    namespace = "com.epmedu.animeal.profile"
}

dependencies {
    implementation(projects.library.common)
    implementation(projects.library.extensions)
    implementation(projects.library.foundation)
    implementation(projects.library.resources)

    implementation(libs.androidx.datastore)
    implementation(libs.androidx.viewmodel)

    implementation(libs.compose.material)
    implementation(libs.compose.material.dialog.core)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.preview)
    debugImplementation(libs.compose.ui.tooling)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}