import com.epmedu.animeal.extension.debug
import com.epmedu.animeal.extension.release

plugins {
    id("AnimealPlugin")
    id("com.android.application")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

animealPlugin {
    buildConfigGeneration = true
}

android {
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
            extra["enableCrashlytics"] = true
            extra["alwaysUpdateBuildId"] = true
        }

        debug {
            extra["enableCrashlytics"] = false
            extra["alwaysUpdateBuildId"] = false
        }
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }

    packagingOptions {
        resources.excludes.add("META-INF/AL2.0")
        resources.excludes.add("META-INF/LGPL2.1")
    }
}

secrets {
    propertiesFileName = "secrets.properties"
}

dependencies {
    implementation(projects.feature.login)
    implementation(projects.feature.splash)
    implementation(projects.feature.tabs)

    implementation(projects.library.common)
    implementation(projects.library.foundation)
    implementation(projects.library.navigation)
    implementation(projects.library.resources)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)

    // Support for Java 8 features
    coreLibraryDesugaring(libs.desugarJdkLibs)

    implementation(libs.amplify.core)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core)
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.viewmodel)
    implementation(libs.androidx.viewmodel.compose)

    implementation(libs.compose.ui)
    implementation(libs.compose.ui.preview)
    implementation(libs.compose.material)

    debugImplementation(libs.compose.ui.tooling)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.test.espresso)
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