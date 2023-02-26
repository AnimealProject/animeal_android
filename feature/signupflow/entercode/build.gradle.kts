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
    implementation(platform(libs.androidx.compose.bom))

    implementation(projects.library.auth)
    implementation(projects.library.common)
    implementation(projects.library.extensions)
    implementation(projects.library.foundation)
    implementation(projects.library.navigation)
    implementation(projects.library.resources)

    implementation(projects.shared.feature.profile)
    implementation(projects.shared.feature.networkuser)

    implementation(libs.amplify.aws.auth.cognito)

    implementation(libs.androidx.datastore)
    implementation(libs.androidx.viewmodel)

    implementation(libs.immutable.collections)

    implementation(libs.compose.material)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.preview)
    debugImplementation(libs.compose.ui.tooling)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}