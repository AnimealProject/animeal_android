import com.epmedu.animeal.extension.debug
import com.epmedu.animeal.extension.release

plugins {
    id("AnimealPlugin")
    id("com.android.application")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("org.jetbrains.kotlin.plugin.compose")
}

animealPlugin {
    buildConfigGeneration = true
}

android {
    namespace = "com.epmedu.animeal"
    signingConfigs {
        debug {
            storeFile = keyStoreFile("debug.keystore")
            storePassword = "android"
            keyAlias = "androiddebugkey"
            keyPassword = "android"
        }

        release {
            storeFile = keyStoreFile("animeal_key_store.jks", "debug.keystore")
            storePassword = System.getenv("KEY_STORE_PASSWORD") ?: "android"
            keyAlias = System.getenv("KEY_ALIAS") ?: "androiddebugkey"
            keyPassword = System.getenv("KEY_PASSWORD") ?: "android"
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
            extra["alwaysUpdateBuildId"] = true
        }

        debug {
            extra["alwaysUpdateBuildId"] = false
        }
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }

    packaging {
        resources.excludes.add("META-INF/AL2.0")
        resources.excludes.add("META-INF/LGPL2.1")
    }
}

dependencies {
    debugImplementation(projects.feature.debugmenu)

    implementation(projects.feature.signupflow.signup)
    implementation(projects.feature.splash)
    implementation(projects.feature.tabsflow.tabs)

    implementation(projects.shared.feature.networkuser)
    implementation(projects.shared.feature.profile)
    implementation(projects.shared.feature.router)

    implementation(projects.library.common)
    implementation(projects.library.extensions)
    implementation(projects.library.foundation)
    implementation(projects.library.navigation)
    implementation(projects.library.resources)

    implementation(platform(libs.firebase.bom))
    releaseImplementation(libs.firebase.crashlytics)

    // Support for Java 8 features
    coreLibraryDesugaring(libs.desugarJdkLibs)

    implementation(libs.amplify.aws.api)
    implementation(libs.amplify.aws.auth.cognito)
    implementation(libs.amplify.core)
    implementation(libs.amplify.storage)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core)
    implementation(libs.androidx.datastore)
    implementation(libs.androidx.lifecycle)
    implementation(libs.androidx.viewmodel)
    implementation(libs.androidx.viewmodel.compose)

    implementation(libs.compose.ui)
    implementation(libs.compose.ui.preview)
    implementation(libs.compose.material)

    implementation(libs.play.services.location)

    debugImplementation(libs.compose.ui.tooling)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.test.espresso)

    debugImplementation(libs.leakcanaryAndroid)
}

fun keyStoreFile(vararg fileNames: String): File? {
    for (path in fileNames) {
        val file = project.file(path)

        if (file.exists()) {
            return file
        }
    }

    return null
}