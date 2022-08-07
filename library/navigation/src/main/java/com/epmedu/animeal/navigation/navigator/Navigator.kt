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
}
