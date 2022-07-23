plugins {
    id("AnimealPlugin")
    id("com.android.library")
}

dependencies {
    api(libs.accompanist.navigation.animation)
    api(libs.androidx.navigation.compose)
    api(libs.androidx.navigation.compose.hilt)
}
