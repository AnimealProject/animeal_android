package com.epmedu.animeal.navigation.navBuilder

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

internal class DefaultNavBuilder(
    private val navGraphBuilder: NavGraphBuilder,
) : NavBuilder.Default {

    override fun NavBuilder.screen(
        route: String,
        arguments: List<NamedNavArgument>,
        deepLinks: List<NavDeepLink>,
        content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
    ) {
        navGraphBuilder.composable(
            route = route,
            arguments = arguments,
            deepLinks = deepLinks,
            content = content,
        )
    }
}
