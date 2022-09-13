plugins {
    id("AnimealPlugin")
    id("com.android.library")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

animealPlugin {
    compose = false
}

dependencies {
    implementation(projects.library.foundation)
    implementation(libs.androidx.datastore)
    implementation(libs.hilt.android)
    implementation(libs.androidx.viewmodel)
    implementation(project(mapOf("path" to ":library:foundation")))
    kapt(libs.hilt.compiler)
}
