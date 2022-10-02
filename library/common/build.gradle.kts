plugins {
    id("animeal.library")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

animealPlugin {
    compose = false
    buildConfigGeneration = false
}

android {
    namespace = "com.epmedu.animeal.common"
}

dependencies {
    implementation(libs.androidx.datastore)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}
