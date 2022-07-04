plugins {
    id("AnimealPlugin")
    id("com.android.library")
}

dependencies {
    implementation(projects.base)

    api(libs.androidx.navigation.compose)
    api(libs.accompanist.navigation.animation)

    implementation(libs.compose.ui)
    implementation(libs.compose.ui.preview)
    implementation(libs.compose.material)

    debugImplementation(libs.compose.ui.tooling)
}
