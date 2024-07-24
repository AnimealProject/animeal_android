import com.moowork.gradle.node.NodeExtension

plugins {
    id("AnimealPlugin")
    id("com.android.library")
    id("com.amazonaws.appsync")
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.epmedu.animeal.token"
}

configure<NodeExtension> {
    distBaseUrl = null
    download = true
    version = "19.6.0"
}

animealPlugin {
    compose = false
}

dependencies {
    implementation(projects.library.codegen)
    implementation(projects.library.common)
    implementation(projects.library.extensions)

    implementation(libs.amplify.aws.auth.cognito)
    implementation(libs.amplify.core)
    implementation(libs.androidx.lifecycle)
    implementation(libs.appsync)
    implementation(libs.gson)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
}
