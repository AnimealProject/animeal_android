plugins {
    id("AnimealPlugin")
    id("com.android.library")
}

android {
    namespace = "com.epmedu.animeal.foundation"
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))

    implementation(projects.library.extensions)
    implementation(projects.library.resources)

    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.accompanist.pager.indicator)

    implementation(libs.compose.ui)
    implementation(libs.compose.ui.preview)
    implementation(libs.compose.material)
    implementation(libs.compose.material.dialog.core)
    implementation(libs.compose.material.dialog.datetime)

    debugImplementation(libs.compose.ui.tooling)
}
