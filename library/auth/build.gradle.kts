plugins {
    id("AnimealPlugin")
    id("com.android.library")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.epmedu.animeal.auth"
}

dependencies {
    implementation(projects.library.common)
    implementation(projects.library.extensions)

    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.androidx.appcompat)
    implementation(libs.compose.runtime)

    implementation(libs.amplify.core)
    implementation(libs.amplify.aws.auth.cognito)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}
