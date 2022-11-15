plugins {
    id("AnimealPlugin")
    id("com.android.library")
    id("kotlin-kapt")
}

android {
    namespace = "com.epmedu.animeal.auth"
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.androidx.appcompat)
    implementation(libs.compose.runtime)

    implementation(libs.amplify.core)
    implementation(libs.amplify.aws.auth.cognito)
}