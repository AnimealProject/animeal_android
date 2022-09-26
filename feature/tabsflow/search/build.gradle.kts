plugins {
    id("AnimealPlugin")
    id("com.android.library")
}

android {
    namespace = "com.epmedu.animeal.tabs.search"
}

dependencies {
    implementation(projects.library.foundation)
    implementation(projects.library.resources)

    implementation(libs.compose.material)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.preview)

    debugImplementation(libs.compose.ui.tooling)
}
