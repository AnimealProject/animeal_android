package com.epmedu.animeal

import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryPlugin
import com.epmedu.animeal.configure.configureAndroidApplication
import com.epmedu.animeal.configure.configureAndroidLibrary
import com.epmedu.animeal.configure.configureBuildFeatures
import com.epmedu.animeal.configure.configureKotlin
import com.epmedu.animeal.internal.applicationExtension
import com.epmedu.animeal.internal.libraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create

open class AnimealPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project) {
            plugins.all {
                when (this) {
                    is AppPlugin -> configureAndroidApplication()
                    is LibraryPlugin -> configureAndroidLibrary()
                }
            }
        }

        val pluginExtension = project.extensions.create<AnimealPluginExtension>("animealPlugin")

        project.afterEvaluate {
            configureKotlin()

            plugins.all {
                when (this) {
                    is AppPlugin -> applicationExtension.configureBuildFeatures(pluginExtension)
                    is LibraryPlugin -> libraryExtension.configureBuildFeatures(pluginExtension)
                }
            }
        }
    }
}