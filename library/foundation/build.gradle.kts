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

    implementation(libs.androidx.lifecycle)

    implementation(libs.compose.material)
    implementation(libs.compose.richtext)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.preview)

    implementation(libs.htmlText)

    debugImplementation(libs.compose.ui.tooling)
}
