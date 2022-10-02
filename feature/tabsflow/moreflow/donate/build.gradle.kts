plugins {
    id("animeal.library")
}

animealPlugin {
    compose = true
    buildConfigGeneration = false
}

android {
    namespace = "com.epmedu.animeal.tabs.more.donate"
}

dependencies {
    implementation(projects.library.extensions)
    implementation(projects.library.foundation)
    implementation(projects.library.navigation)
    implementation(projects.library.resources)

    implementation(libs.compose.ui)
    implementation(libs.compose.ui.preview)
    debugImplementation(libs.compose.ui.tooling)
}