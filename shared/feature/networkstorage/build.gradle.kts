plugins {
    id("AnimealPlugin")
    id("com.android.library")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.epmedu.animeal.networkstorage"
}

animealPlugin {
    compose = false
}

dependencies {
    implementation(projects.library.api)
    implementation(projects.library.common)
    implementation(projects.library.extensions)
    implementation(projects.library.token)

    implementation(libs.amplify.aws.api)
    implementation(libs.amplify.core)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}