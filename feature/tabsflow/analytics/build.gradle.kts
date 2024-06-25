plugins {
    id("AnimealPlugin")
    id("com.android.library")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.epmedu.animeal.tabs.analytics"
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))

    implementation(projects.library.foundation)
    implementation(projects.library.resources)

    implementation(libs.compose.material)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.preview)

    debugImplementation(libs.compose.ui.tooling)
}
