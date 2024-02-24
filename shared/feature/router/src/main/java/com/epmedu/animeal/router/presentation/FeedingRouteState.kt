package com.epmedu.animeal.router.presentation

import com.mapbox.navigation.base.route.NavigationRoute

sealed interface FeedingRouteState {

    object Disabled : FeedingRouteState

    data class Active(
        val showFullRoad: Boolean = false,
        val distanceLeft: Long? = null,
        val routeData: NavigationRoute? = null,
        val isError: Boolean = false
    ) : FeedingRouteState
}
