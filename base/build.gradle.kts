plugins {
    id("AnimalisPlugin")
    id("com.android.library")
}

dependencies {
    val composeVersion: String by rootProject.extra
    val activityComposeVersion: String by rootProject.extra

    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    implementation("androidx.activity:activity-compose:$activityComposeVersion")

    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")
}