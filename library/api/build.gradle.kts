plugins {
    id("AnimealPlugin")
    id("com.android.library")
    id("com.amazonaws.appsync")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.epmedu.animeal.api"

    node {
        download = true
        // Do not declare the repository
        distBaseUrl = null
    }
}

animealPlugin {
    compose = false
}

dependencies {
    implementation(projects.library.codegen)
    implementation(projects.library.extensions)

    implementation(libs.amplify.aws.api)
    implementation(libs.amplify.core)
    implementation(libs.androidx.lifecycle)
    implementation(libs.appsync)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}
