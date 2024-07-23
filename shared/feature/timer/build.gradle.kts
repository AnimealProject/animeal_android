plugins {
    id("AnimealPlugin")
    id("com.android.library")
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.epmedu.animeal.timer"
}

animealPlugin {
    compose = false
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))

    implementation(projects.library.common)

    implementation(libs.compose.material)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.preview)
    implementation(projects.shared.feature.router)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    debugImplementation(libs.compose.ui.tooling)
}