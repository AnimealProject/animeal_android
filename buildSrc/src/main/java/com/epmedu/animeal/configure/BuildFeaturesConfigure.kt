package com.epmedu.animeal.configure

import com.android.build.api.dsl.CommonExtension
import com.epmedu.animeal.AnimealPluginExtension

fun CommonExtension<*, *, *, *, *>.configureBuildFeatures(
    pluginExtension: AnimealPluginExtension
) {
    buildFeatures {
        buildConfig = pluginExtension.buildConfigGeneration

        if (pluginExtension.compose) {
            compose = pluginExtension.compose
        }
    }
}