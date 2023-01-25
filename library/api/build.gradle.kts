plugins {
    id("AnimealPlugin")
    id("com.android.library")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.epmedu.animeal.api"
}

animealPlugin {
    compose = false
}

dependencies {
    implementation(projects.library.extensions)
    implementation(projects.library.generatedmodel)

    implementation(libs.amplify.aws.api)
    implementation(libs.amplify.core)
    implementation(libs.androidx.lifecycle)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}
