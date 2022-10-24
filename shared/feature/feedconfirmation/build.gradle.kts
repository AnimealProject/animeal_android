plugins {
    id("AnimealPlugin")
    id("com.android.library")
    id("kotlin-kapt")
}

android {
    namespace = "com.epmedu.animeal.feedconfirmation"
}

dependencies {
    implementation(projects.library.common)
    implementation(projects.library.extensions)
    implementation(projects.library.foundation)
    implementation(projects.library.resources)

    implementation(libs.androidx.datastore)
    implementation(libs.androidx.viewmodel)

    implementation(libs.compose.material)
    implementation(libs.compose.material.dialog.core)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.preview)
}