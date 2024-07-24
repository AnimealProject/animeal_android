plugins {
    id("AnimealPlugin")
    id("com.android.library")
    id("com.google.devtools.ksp")
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
    ksp(libs.hilt.compiler)
}
