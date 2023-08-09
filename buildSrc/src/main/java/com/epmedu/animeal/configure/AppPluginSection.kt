package com.epmedu.animeal.configure

import com.epmedu.animeal.extension.provideVersionCode
import com.epmedu.animeal.extension.provideVersionName
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
        versionCode = provideVersionCode()
        versionName = provideVersionName()

        addMapBoxPublicKeyField()
        addMapBoxStyleURIField()

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

    flavorDimensions += "environment"
    productFlavors {
        create("dev") {
            dimension = "environment"
            versionName = "dev." + provideVersionName()
        }
        create("qa") {
            dimension = "environment"
            versionName = "test." + provideVersionName()
        }
        create("prod") {
            dimension = "environment"
        }
    }
}
