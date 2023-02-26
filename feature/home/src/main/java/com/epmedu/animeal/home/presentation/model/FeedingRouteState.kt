package com.epmedu.animeal.home.presentation.model

import com.mapbox.navigation.base.route.NavigationRoute

sealed class FeedingRouteState(
    val timeLeft: Long? = null,
    val distanceLeft: Long? = null,
    val isRouteActive: Boolean = false,
    val routeData: NavigationRoute? = null,
    val isError: Boolean = false
) {
    object Disabled : FeedingRouteState(
        timeLeft = null,
        distanceLeft = null,
        isRouteActive = false,
        isError = false
    )

    class Active(
        distanceLeft: Long? = null,
        timeLeft: Long? = null,
        routeData: NavigationRoute? = null,
        isError: Boolean = false
    ) : FeedingRouteState(
        timeLeft = timeLeft,
        distanceLeft = distanceLeft,
        isRouteActive = true,
        routeData = routeData,
        isError = isError
    )
}
