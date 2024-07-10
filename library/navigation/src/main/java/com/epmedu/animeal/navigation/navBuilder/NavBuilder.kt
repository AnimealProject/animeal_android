package com.epmedu.animeal.navigation.navBuilder

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
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
            content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
        )
    }

    interface Animated : NavBuilder {

        fun NavBuilder.screen(
            route: String,
            arguments: List<NamedNavArgument> = emptyList(),
            deepLinks: List<NavDeepLink> = emptyList(),
            enterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = null,
            exitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = null,
            popEnterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = enterTransition,
            popExitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = exitTransition,
            content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
        )
    }
}
