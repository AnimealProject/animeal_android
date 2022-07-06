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
}
