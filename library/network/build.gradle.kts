plugins {
    id("AnimealPlugin")
    id("com.android.library")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.epmedu.animeal.network"
}

animealPlugin {
    compose = false
}

dependencies {
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}