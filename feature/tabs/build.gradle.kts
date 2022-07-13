plugins {
    id("AnimealPlugin")
    id("com.android.library")
}

dependencies {
    implementation(projects.base)

    implementation(projects.feature.more)
    implementation(projects.feature.home)

    implementation(projects.library.extensions)
    implementation(projects.library.foundation)
    implementation(projects.library.navigation)
    implementation(projects.library.resources)

    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pager.indicators)

    implementation(libs.androidx.viewmodel)
    implementation(libs.androidx.viewmodel.compose)

    implementation(libs.compose.ui)
    implementation(libs.compose.ui.preview)
    implementation(libs.compose.material)

    debugImplementation(libs.compose.ui.tooling)
}
