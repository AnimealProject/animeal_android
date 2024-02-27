package com.epmedu.animeal.configure

import com.epmedu.animeal.internal.libraryExtension
import com.epmedu.animeal.internal.libs
import org.gradle.api.JavaVersion
import org.gradle.api.Project

internal fun Project.configureAndroidLibrary() = libraryExtension.run {
    plugins.apply("kotlin-android")

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        compileSdk = libs.versions.compileSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        debug {
            isMinifyEnabled = false
        }
    }
}