plugins {
    id("AnimealPlugin")
    id("com.android.library")
}

dependencies {
    implementation(projects.library.resources)

    implementation(projects.library.resources)

    implementation(libs.compose.ui)
    implementation(libs.compose.ui.preview)
    implementation(libs.compose.material)

    // https://developer.android.com/studio/known-issues#error_when_rendering_compose_preview
    debugImplementation(libs.androidx.lifecycle.runtime)
    debugImplementation(libs.androidx.lifecycle.savedstate)
    debugImplementation(libs.compose.ui.tooling)
}
