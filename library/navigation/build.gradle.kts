plugins {
    id("animeal.library")
}

animealPlugin {
    compose = true
    buildConfigGeneration = false
}

android {
    namespace = "com.epmedu.animeal.navigation"
}

dependencies {
    api(libs.accompanist.navigation.animation)
    api(libs.androidx.navigation.compose)
    api(libs.androidx.navigation.compose.hilt)
}
