plugins {
    id("AnimealPlugin")
    id("com.android.library")
}

android {
    namespace = "com.epmedu.animeal.navigation"
}

dependencies {
    api(libs.androidx.navigation.compose)
    api(libs.androidx.navigation.compose.hilt)
}
