package com.epmedu.animeal.navigation.navBuilder

import androidx.compose.animation.*
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink

sealed interface NavBuilder {

    interface Default : NavBuilder {

        fun NavBuilder.screen(
            route: String,
            arguments: List<NamedNavArgument> = emptyList(),
            deepLinks: List<NavDeepLink> = emptyList(),
            content: @Composable (NavBackStackEntry) -> Unit,
        )
    }

    interface Animated : NavBuilder {

        @Suppress("LongParameterList")
        @OptIn(ExperimentalAnimationApi::class)
        fun NavBuilder.screen(
            route: String,
            arguments: List<NamedNavArgument> = emptyList(),
            deepLinks: List<NavDeepLink> = emptyList(),
            enterTransition: (AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition?)? = null,
            exitTransition: (AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition?)? = null,
            popEnterTransition: (AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition?)? = enterTransition,
            popExitTransition: (AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition?)? = exitTransition,
            content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
        )
    }
}
