package com.epmedu.animeal.home.presentation.model

import com.mapbox.navigation.base.route.NavigationRoute

sealed interface FeedingRouteState {

    val isRouteActive: Boolean get() = this is Active

    object Disabled : FeedingRouteState

    data class Active(
        val distanceLeft: Long? = null,
        val timeLeft: Long? = null,
        val routeData: NavigationRoute? = null,
        val isError: Boolean = false
    ) : FeedingRouteState
}
