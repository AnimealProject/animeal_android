plugins {
    id("AnimealPlugin")
    id("com.android.library")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.epmedu.animeal.debugmenu"
}

dependencies {
    debugImplementation(platform(libs.androidx.compose.bom))

    debugImplementation(projects.library.common)
    debugImplementation(projects.library.extensions)
    debugImplementation(projects.library.foundation)
    debugImplementation(projects.library.navigation)

    debugImplementation(projects.shared.feature.debugmenu)
    debugImplementation(projects.shared.feature.router)

    debugImplementation(libs.compose.ui)
    debugImplementation(libs.compose.ui.preview)
    debugImplementation(libs.compose.material)
    debugImplementation(libs.androidx.viewmodel)
    debugImplementation(libs.androidx.viewmodel.compose)

    debugImplementation(libs.compose.ui.tooling)

    debugImplementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}
