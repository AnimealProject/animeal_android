package com.epmedu.animeal.navigation.navigator

import androidx.navigation.NavOptionsBuilder

interface Navigator {

    val parent: Navigator?

    fun navigate(
        route: String,
        builder: NavOptionsBuilder.() -> Unit = {}
    )

    fun popBackStack()

    fun popBackStack(
        route: String,
        inclusive: Boolean = false,
        saveState: Boolean = false
    )

    /**
     * Attempts to pop the navigator's back stack back to a specific route.
     * If route is not present in the back stack, navigates to it.
     */
    fun popBackStackOrNavigate(
        route: String,
        inclusive: Boolean = false,
        saveState: Boolean = false
    )
}
