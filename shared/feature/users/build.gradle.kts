plugins {
    id("AnimealPlugin")
    id("com.android.library")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.epmedu.animeal.users"
}

animealPlugin {
    compose = false
}

dependencies {
    implementation(projects.library.api)
    implementation(projects.library.common)
    implementation(projects.library.extensions)

    implementation(libs.amplify.aws.api)
    implementation(libs.amplify.core)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}