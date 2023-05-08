plugins {
    id("AnimealPlugin")
    id("com.android.library")
}

android {
    namespace = "com.epmedu.animeal.shared.appsettings"
}

dependencies {
    api(libs.androidx.datastore)
}