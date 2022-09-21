plugins {
    id("AnimealPlugin")
    id("com.android.library")
}

android {
    namespace = "com.epmedu.animeal"
}

dependencies {
    implementation(projects.feature.home)
    implementation(projects.feature.mainflow.analytics)
    implementation(projects.feature.mainflow.moreflow.flow)
    implementation(projects.feature.mainflow.search)

    implementation(projects.library.foundation)
    implementation(projects.library.navigation)
    implementation(projects.library.resources)

    implementation(libs.compose.material)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.preview)

    debugImplementation(libs.compose.ui.tooling)
}
