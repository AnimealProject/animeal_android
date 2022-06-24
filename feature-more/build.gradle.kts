plugins {
    id("AnimealPlugin")
    id("com.android.library")
}

dependencies {
    implementation(projects.base)

    val composeVersion: String by rootProject.extra
    val activityComposeVersion: String by rootProject.extra
    val lifecycleKtxVersion: String by rootProject.extra
    val navigationComposeVersion: String by rootProject.extra
    val accompanistVersion: String by rootProject.extra

    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    implementation("androidx.activity:activity-compose:$activityComposeVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleKtxVersion")
    implementation("androidx.navigation:navigation-compose:$navigationComposeVersion")
    implementation("com.google.accompanist:accompanist-navigation-animation:$accompanistVersion")

    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")
}