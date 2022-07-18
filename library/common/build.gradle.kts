import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

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
    implementation(libs.androidx.preference)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}
