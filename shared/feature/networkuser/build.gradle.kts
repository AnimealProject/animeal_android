plugins {
    id("AnimealPlugin")
    id("com.android.library")
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.epmedu.animeal.networkuser"
}

animealPlugin {
    compose = false
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))

    implementation(projects.library.auth)
    implementation(projects.library.common)
    implementation(projects.library.extensions)
    implementation(projects.library.foundation)
    implementation(projects.library.resources)

    debugImplementation(projects.shared.feature.debugmenu)
    implementation(projects.shared.feature.profile)
    implementation(projects.shared.feature.router)
    implementation(projects.shared.feature.users)

    implementation(libs.androidx.datastore)
    implementation(libs.androidx.viewmodel)
    implementation(libs.amplify.aws.auth.cognito)

    implementation(libs.compose.material)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.preview)
    debugImplementation(libs.compose.ui.tooling)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
}