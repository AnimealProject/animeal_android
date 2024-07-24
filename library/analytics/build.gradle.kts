plugins {
    id("AnimealPlugin")
    id("com.android.library")
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
}

animealPlugin {
    compose = false
}

android {
    namespace = "com.epmedu.animeal.analytics"
}

dependencies {
    implementation(libs.hilt.android)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)

    ksp(libs.hilt.compiler)
}