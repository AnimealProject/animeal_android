plugins {
    id("AnimealPlugin")
    id("com.android.library")
}

dependencies {
    implementation(libs.androidx.activity.compose)

    implementation(libs.compose.ui)
    implementation(libs.compose.ui.preview)
    implementation(libs.compose.material)

    debugImplementation(libs.compose.ui.tooling)

    // Temporary fix for preview bug https://issuetracker.google.com/issues/227767363
    debugApi(libs.androidx.custom.view)
    debugApi(libs.androidx.custom.view.container)
}