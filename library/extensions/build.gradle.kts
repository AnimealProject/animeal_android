plugins {
    id("AnimealPlugin")
    id("com.android.library")
}

android {
    namespace = "com.epmedu.animeal.extensions"
}

dependencies {
    implementation(libs.androidx.datastore)
    implementation(projects.library.common)
    implementation(projects.library.resources)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.appcompat)
    implementation(libs.compose.runtime)
    implementation(libs.play.services.location)
}