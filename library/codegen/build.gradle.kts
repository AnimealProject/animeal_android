import com.moowork.gradle.node.NodeExtension

plugins {
    id("AnimealPlugin")
    id("com.android.library")
    id("com.amazonaws.appsync")
}

android {
    namespace = "com.amplifyframework.datastore.generated.model"
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
    implementation(libs.amplify.aws.api)
    implementation(libs.amplify.core)

    implementation(libs.appsync)
}