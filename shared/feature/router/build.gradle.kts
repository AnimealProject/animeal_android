plugins {
    id("AnimealPlugin")
    id("com.android.library")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.epmedu.animeal.router"
}

animealPlugin {
    compose = false
}

dependencies {
    implementation(projects.library.common)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}