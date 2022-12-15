package com.epmedu.animeal.home.presentation.model

import com.mapbox.navigation.base.route.NavigationRoute

sealed class FeedingRouteState(
    val timeLeft: Long? = null,
    val distanceLeft: String? = null,
    val isRouteActive: Boolean = false,
    val routeData: NavigationRoute? = null
) {
    object Disabled : FeedingRouteState(null, null, false)
    object Started : FeedingRouteState(isRouteActive = true)
    class Updated(
        distanceLeft: String? = null,
        timeLeft: Long? = null,
        routeData: NavigationRoute? = null
    ) : FeedingRouteState(timeLeft, distanceLeft, true, routeData)
}
