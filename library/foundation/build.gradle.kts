plugins {
    id("AnimealPlugin")
    id("com.android.library")
}

dependencies {
    implementation(projects.library.resources)

    implementation(libs.accompanist.systemuicontroller)

    implementation(libs.compose.ui)
    implementation(libs.compose.ui.preview)
    implementation(libs.compose.material)
    implementation(libs.compose.material.dialog.core)
    implementation(libs.compose.material.dialog.datetime)

    // https://developer.android.com/studio/known-issues#error_when_rendering_compose_preview
    debugImplementation(libs.compose.ui.tooling)
}
