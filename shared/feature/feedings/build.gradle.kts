plugins {
    id("AnimealPlugin")
    id("com.android.library")
    id("com.amazonaws.appsync")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.epmedu.animeal.feedings"
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
    implementation(projects.library.resources)
    implementation(projects.library.navigation)

    implementation(projects.shared.feature.feeding)
    implementation(projects.shared.feature.networkuser)
    implementation(projects.shared.feature.users)

    implementation(libs.amplify.aws.api)
    implementation(libs.amplify.core)
    implementation(libs.appsync)
    implementation(libs.immutable.collections)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose.hilt)
    implementation(libs.androidx.viewmodel)
    implementation(libs.coil)
    implementation(libs.compose.material)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.preview)
    debugImplementation(libs.compose.ui.tooling)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}