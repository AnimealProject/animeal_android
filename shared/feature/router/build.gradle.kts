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
    implementation(libs.mapbox.android)
    implementation(libs.mapbox.navigation)
    //implementation(projects.shared.feature.feeding)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}