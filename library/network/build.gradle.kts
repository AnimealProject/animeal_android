plugins {
    id("AnimealPlugin")
    id("com.android.library")
    id("com.google.devtools.ksp")
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
    ksp(libs.hilt.compiler)
}