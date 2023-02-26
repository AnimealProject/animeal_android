import com.moowork.gradle.node.NodeExtension

plugins {
    id("AnimealPlugin")
    id("com.android.library")
    id("com.amazonaws.appsync")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.epmedu.animeal.api"
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
    implementation(projects.library.extensions)

    implementation(libs.amplify.aws.api)
    implementation(libs.amplify.core)
    implementation(libs.androidx.lifecycle)
    implementation(libs.appsync)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}
