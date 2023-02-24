plugins {
    id("AnimealPlugin")
    id("com.android.library")
    id("com.amazonaws.appsync")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.epmedu.animeal.feeding"
}

configure<com.moowork.gradle.node.NodeExtension> {
    distBaseUrl = null
    download = true
    version = "19.6.0"
}

dependencies {
    implementation(projects.library.api)
    implementation(projects.library.auth)
    implementation(projects.library.codegen)
    implementation(projects.library.common)
    implementation(projects.library.extensions)
    implementation(projects.library.foundation)
    implementation(projects.library.geolocation)
    implementation(projects.library.resources)

    implementation(projects.shared.feature.profile)

    implementation(libs.amplify.aws.api)
    implementation(libs.amplify.core)
    implementation(libs.appsync)

    implementation(libs.compose.material)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.preview)
    debugImplementation(libs.compose.ui.tooling)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    implementation(libs.mapbox.android)

}