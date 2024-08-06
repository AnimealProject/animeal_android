plugins {
    id("AnimealPlugin")
    id("com.android.library")
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.epmedu.animeal.users"
}

animealPlugin {
    compose = false
}

dependencies {
    implementation(projects.library.api)
    implementation(projects.library.auth)
    implementation(projects.library.common)
    implementation(projects.library.extensions)

    implementation(libs.amplify.aws.api)
    implementation(libs.amplify.core)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
}