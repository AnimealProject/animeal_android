plugins {
    id("AnimealPlugin")
    id("com.android.library")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
}

android {
    namespace = "com.epmedu.animeal.signup.entercode"
}

dependencies {
    implementation(projects.library.common)
    implementation(projects.library.extensions)
    implementation(projects.library.foundation)
    implementation(projects.library.navigation)
    implementation(projects.library.resources)

    implementation(libs.androidx.datastore)
    implementation(libs.androidx.viewmodel)

    implementation(libs.compose.material)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.preview)
    debugImplementation(libs.compose.ui.tooling)

    implementation(libs.amplify.core)
    implementation(libs.amplify.aws.auth.cognito)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}