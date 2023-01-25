plugins {
    id("AnimealPlugin")
    id("com.android.library")
}

android {
    namespace = "com.amplifyframework.datastore.generated.model"
}

animealPlugin {
    compose = false
}

dependencies {
    implementation(libs.amplify.aws.api)
    implementation(libs.amplify.core)
}