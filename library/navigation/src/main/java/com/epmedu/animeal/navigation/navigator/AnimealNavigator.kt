package com.epmedu.animeal.navigation.navigator

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder

val LocalNavigator = staticCompositionLocalOf<Navigator?> {
    null
}

internal class AnimealNavigator(
    private val navController: NavController,
    override val parent: Navigator?
) : Navigator {

    override fun navigate(route: String, builder: NavOptionsBuilder.() -> Unit) {
        navController.navigate(route, builder)
    }

    override fun popBackStack() {
        navController.popBackStack()
    }

    override fun popBackStack(route: String, inclusive: Boolean, saveState: Boolean) {
        navController.popBackStack(route, inclusive, saveState)
    }

    override fun popBackStackOrNavigate(
        route: String,
        inclusive: Boolean,
        saveState: Boolean
    ) {
        if (navController.popBackStack(route, inclusive, saveState).not()) {
            navController.navigate(route) {
                popUpTo(navController.currentDestination?.route!!) {
                    this.inclusive = true
                }
            }
        }
    }

    override fun navigateTo(route: String) {
        navController.navigate(route) {
            navController.graph.startDestinationRoute?.let {
                popUpTo(it) { saveState = true }
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}
