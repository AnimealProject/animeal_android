plugins {
    id("AnimealPlugin")
    id("com.android.library")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.epmedu.animeal.debugmenu"
}

animealPlugin {
    compose = false
}

dependencies {
    debugImplementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}
