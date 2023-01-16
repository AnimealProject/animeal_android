plugins {
    id("AnimealPlugin")
    id("com.android.library")
    id("com.amazonaws.appsync")
}

android {
    namespace = "com.amplifyframework.datastore.generated.model"

    node {
        download = true
        // Do not declare the repository
        distBaseUrl = null
    }
}

animealPlugin {
    compose = false
}

dependencies {
    implementation(libs.amplify.aws.api)
    implementation(libs.amplify.core)

    implementation(libs.appsync)
}