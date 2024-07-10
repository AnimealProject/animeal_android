package com.epmedu.animeal.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.epmedu.animeal.navigation.navBuilder.AnimatedNavBuilder
import com.epmedu.animeal.navigation.navBuilder.DefaultNavBuilder
import com.epmedu.animeal.navigation.navBuilder.NavBuilder
import com.epmedu.animeal.navigation.navigator.AnimealNavigator
import com.epmedu.animeal.navigation.navigator.LocalNavigator
import com.epmedu.animeal.navigation.navigator.Navigator

@Suppress("ReusedModifierInstance", "ComposableParametersOrdering", "ModifierParameterPosition")
@Composable
fun ScreenNavHost(
    startDestination: String,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    route: String? = null,
    builder: NavBuilder.Default.() -> Unit
) {
    CompositionLocalProvider(
        LocalNavigator provides rememberNavigator(navController),
    ) {
        NavHost(
            modifier = modifier,
            navController = navController,
            startDestination = startDestination,
            route = route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        ) {
            DefaultNavBuilder(this).builder()
        }
    }
}

@Suppress("ReusedModifierInstance", "ComposableParametersOrdering", "ModifierParameterPosition")
@Composable
fun AnimatedScreenNavHost(
    startDestination: String,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    contentAlignment: Alignment = Alignment.Center,
    route: String? = null,
    enterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition) =
        { fadeIn(animationSpec = tween(700)) },
    exitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition) =
        { fadeOut(animationSpec = tween(700)) },
    popEnterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition) = enterTransition,
    popExitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition) = exitTransition,
    builder: NavBuilder.Animated.() -> Unit
) {
    CompositionLocalProvider(
        LocalNavigator provides rememberNavigator(navController),
    ) {
        NavHost(
            startDestination = startDestination,
            modifier = modifier,
            navController = navController,
            contentAlignment = contentAlignment,
            route = route,
            enterTransition = enterTransition,
            exitTransition = exitTransition,
            popEnterTransition = popEnterTransition,
            popExitTransition = popExitTransition,
        ) {
            AnimatedNavBuilder(this).builder()
        }
    }
}

@Composable
private fun rememberNavigator(navController: NavController): Navigator {
    val parent = LocalNavigator.current

    return remember(navController, parent) {
        AnimealNavigator(
            navController,
            parent
        )
    }
}
