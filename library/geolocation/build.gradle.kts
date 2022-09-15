plugins {
    id("AnimealPlugin")
    id("com.android.library")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

animealPlugin {
    compose = false
}

dependencies {
    implementation(projects.library.extensions)

    implementation(libs.play.services.location)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}
