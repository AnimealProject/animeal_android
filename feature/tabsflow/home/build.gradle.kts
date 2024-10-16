plugins {
    id("AnimealPlugin")
    id("com.android.library")
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.epmedu.animeal.home"
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))

    implementation(projects.library.common)
    implementation(projects.library.extensions)
    implementation(projects.library.foundation)
    implementation(projects.library.geolocation)
    implementation(projects.library.navigation)
    implementation(projects.library.resources)

    implementation(projects.shared.feature.camera)
    implementation(projects.shared.feature.feeding)
    implementation(projects.shared.feature.feedings)
    implementation(projects.shared.feature.networkstorage)
    implementation(projects.shared.feature.networkuser)
    implementation(projects.shared.feature.permissions)
    implementation(projects.shared.feature.profile)
    implementation(projects.shared.feature.router)
    implementation(projects.shared.feature.timer)
    implementation(projects.shared.feature.users)

    implementation(libs.accompanist.permissions)

    implementation(libs.mapbox.android)
    implementation(libs.mapbox.navigation)

    implementation(libs.immutable.collections)

    implementation(libs.compose.ui)
    implementation(libs.compose.ui.preview)
    implementation(libs.compose.material)
    implementation(libs.androidx.viewmodel)
    implementation(libs.androidx.viewmodel.compose)

    debugImplementation(libs.compose.ui.tooling)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
}
