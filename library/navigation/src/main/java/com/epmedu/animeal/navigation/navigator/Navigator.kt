package com.epmedu.animeal.navigation.navigator

import androidx.navigation.NavOptionsBuilder

interface Navigator {

    val parent: Navigator?

    fun navigate(
        route: String,
        builder: NavOptionsBuilder.() -> Unit = {}
    )

    fun popBackStack()
}
