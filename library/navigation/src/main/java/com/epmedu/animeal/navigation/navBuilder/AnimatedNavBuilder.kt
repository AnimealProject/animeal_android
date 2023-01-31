package com.epmedu.animeal.navigation.navBuilder

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable

internal class AnimatedNavBuilder(
    private val navGraphBuilder: NavGraphBuilder,
) : NavBuilder.Animated {

    @OptIn(ExperimentalAnimationApi::class)
    override fun NavBuilder.screen(
        route: String,
        arguments: List<NamedNavArgument>,
        deepLinks: List<NavDeepLink>,
        enterTransition: (AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition?)?,
        exitTransition: (AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition?)?,
        popEnterTransition: (AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition?)?,
        popExitTransition: (AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition?)?,
        content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
    ) {
        navGraphBuilder.composable(
            route = route,
            arguments = arguments,
            deepLinks = deepLinks,
            enterTransition = enterTransition,
            exitTransition = exitTransition,
            popEnterTransition = popEnterTransition,
            popExitTransition = popExitTransition,
            content = content,
        )
    }
}
