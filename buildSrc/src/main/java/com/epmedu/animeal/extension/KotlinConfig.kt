package com.epmedu.animeal.extension

import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

fun Project.configureKotlin() {
    configure<KotlinAndroidProjectExtension> {
        jvmToolchain(21)
    }
}