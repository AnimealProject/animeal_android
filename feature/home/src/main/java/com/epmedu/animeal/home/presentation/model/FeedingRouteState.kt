package com.epmedu.animeal.home.presentation.model

sealed class FeedingRouteState(
    val timeLeft: Long? = null,
    val distanceLeft: String? = null,
    val isRouteActive: Boolean = false
) {
    object Disabled : FeedingRouteState(null, null, false)
    object Started : FeedingRouteState(isRouteActive = true)
    class Updated(distanceLeft: String? = null, timeLeft: Long? = null) :
        FeedingRouteState(timeLeft, distanceLeft, true)
}
