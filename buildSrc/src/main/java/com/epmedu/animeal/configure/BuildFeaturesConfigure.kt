package com.epmedu.animeal.configure

import com.android.build.api.dsl.CommonExtension
import com.epmedu.animeal.AnimealPluginExtension
import com.epmedu.animeal.internal.libs
import org.gradle.api.Project

fun CommonExtension<*, *, *, *, *>.configureBuildFeatures(
    project: Project,
    pluginExtension: AnimealPluginExtension
) {
    buildFeatures {
        buildConfig = pluginExtension.buildConfigGeneration

        if (pluginExtension.compose) {
            compose = pluginExtension.compose

            composeOptions {
                kotlinCompilerExtensionVersion = project.libs.versions.composeCompiler.get()
            }
        }
    }
}