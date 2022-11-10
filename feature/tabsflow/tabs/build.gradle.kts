plugins {
    id("AnimealPlugin")
    id("com.android.library")
}

android {
    namespace = "com.epmedu.animeal.tabs"
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))

    implementation(projects.feature.home)
    implementation(projects.feature.tabsflow.analytics)
    implementation(projects.feature.tabsflow.moreflow.host)
    implementation(projects.feature.tabsflow.search)
    implementation(projects.feature.tabsflow.favourites)

    implementation(projects.library.foundation)
    implementation(projects.library.navigation)
    implementation(projects.library.resources)

    implementation(libs.compose.material)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.preview)

    debugImplementation(libs.compose.ui.tooling)
}
