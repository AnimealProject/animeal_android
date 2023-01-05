package com.epmedu.animeal.home.presentation.model

import com.mapbox.navigation.base.route.NavigationRoute

sealed class FeedingRouteState(
    val timeLeft: Long? = null,
    val distanceLeft: Long? = null,
    val isRouteActive: Boolean = false,
    val routeData: NavigationRoute? = null
) {
    object Disabled : FeedingRouteState(timeLeft = null, distanceLeft = null, isRouteActive = false)
    object Started : FeedingRouteState(isRouteActive = true)
    class Updated(
        distanceLeft: Long? = null,
        timeLeft: Long? = null,
        routeData: NavigationRoute? = null
    ) : FeedingRouteState(
        timeLeft = timeLeft,
        distanceLeft = distanceLeft,
        isRouteActive = true,
        routeData = routeData
    )
}
