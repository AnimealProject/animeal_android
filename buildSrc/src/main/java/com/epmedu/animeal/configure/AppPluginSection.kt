package com.epmedu.animeal.configure

import com.epmedu.animeal.internal.applicationExtension
import com.epmedu.animeal.internal.libs
import org.gradle.api.Project

internal fun Project.configureAndroidApplication() = applicationExtension.run {
    plugins.apply("kotlin-android")

    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.epmedu.animeal"
        targetSdk = libs.versions.targetSdk.get().toInt()
        minSdk = libs.versions.minSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                "proguard-rules.pro",
                getDefaultProguardFile("proguard-android-optimize.txt")
            )
        }

        debug {
            isMinifyEnabled = false
        }
    }
}
