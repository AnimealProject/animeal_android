plugins {
    id("AnimealPlugin")
    id("com.android.library")
}

android {
    namespace = "com.epmedu.animeal.feedconfirmation"
}

dependencies {
    implementation(projects.library.common)
    implementation(projects.library.extensions)
    implementation(projects.library.navigation)
    implementation(projects.library.foundation)
    implementation(projects.library.resources)

    implementation(projects.shared.feature.feeding)

    implementation(libs.compose.material)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.preview)
}