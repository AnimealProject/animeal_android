plugins {
    id("AnimealPlugin")
    id("com.android.library")
}

android {
    namespace = "com.epmedu.animeal.extensions"
}

dependencies {
    implementation(libs.compose.runtime)
}
