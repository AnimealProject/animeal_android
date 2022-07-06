plugins {
    id("AnimealPlugin")
    id("com.android.library")
}

dependencies {
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.preview)
    implementation(libs.compose.material)

    debugImplementation(libs.compose.ui.tooling)
}
