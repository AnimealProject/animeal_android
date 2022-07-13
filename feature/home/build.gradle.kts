plugins {
    id("AnimealPlugin")
    id("com.android.library")
}

dependencies {
    implementation(projects.base)

    implementation(projects.library.common)
    implementation(projects.library.extensions)
    implementation(projects.library.foundation)
    implementation(projects.library.navigation)
    implementation(projects.library.resources)

    implementation(libs.accompanist.permissions)

    implementation(libs.mapbox.android)

    implementation(libs.compose.ui)
    implementation(libs.compose.ui.preview)
    implementation(libs.compose.material)

    debugImplementation(libs.compose.ui.tooling)
}
