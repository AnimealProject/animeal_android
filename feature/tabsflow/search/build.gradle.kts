plugins {
    id("AnimealPlugin")
    id("com.android.library")
}

android {
    namespace = "com.epmedu.animeal.tabs.search"
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))

    implementation(projects.library.foundation)
    implementation(projects.library.resources)

    implementation(libs.compose.material)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.preview)

    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pager.indicator)

    debugImplementation(libs.compose.ui.tooling)
}
