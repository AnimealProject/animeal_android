package com.epmedu.animeal

import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryPlugin
import com.epmedu.animeal.configure.configureAndroidApplication
import com.epmedu.animeal.configure.configureAndroidLibrary
import com.epmedu.animeal.configure.configureBuildFeatures
import com.epmedu.animeal.internal.applicationExtension
import com.epmedu.animeal.internal.libraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create

open class AnimalisPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project) {
            plugins.all {
                when (this) {
                    is AppPlugin -> configureAndroidApplication()
                    is LibraryPlugin -> configureAndroidLibrary()
                }
            }
        }

        val pluginExtension = project.extensions.create<AnimalisPluginExtension>("animalisPlugin")

        project.afterEvaluate {
            plugins.all {
                when (this) {
                    is AppPlugin -> applicationExtension
                        .configureBuildFeatures(
                            project = project,
                            pluginExtension = pluginExtension
                        )
                    is LibraryPlugin -> libraryExtension
                        .configureBuildFeatures(
                            project = project,
                            pluginExtension = pluginExtension
                        )
                }
            }
        }
    }
}