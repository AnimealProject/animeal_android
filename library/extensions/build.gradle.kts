plugins {
    id("AnimealPlugin")
    id("com.android.library")
}

android {
    namespace = "com.epmedu.animeal.extensions"
}

dependencies {
    implementation(projects.library.resources)

    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.amplify.aws.api)
    implementation(libs.androidx.appcompat)
    implementation(projects.library.resources)
    implementation(libs.compose.runtime)
}
