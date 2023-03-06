package com.epmedu.animeal.home.presentation.model

import com.mapbox.navigation.base.route.NavigationRoute

sealed interface FeedingRouteState {

    object Disabled : FeedingRouteState

    data class Active(
        val distanceLeft: Long? = null,
        val timeLeft: Long? = null,
        val routeData: NavigationRoute? = null,
        val isError: Boolean = false
    ) : FeedingRouteState
}
