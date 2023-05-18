package com.epmedu.animeal.router.model

import com.mapbox.navigation.base.route.NavigationRoute

data class RouteResult(
    val isSuccessful: Boolean,
    val timeLeft: Long? = null,
    val routeData: NavigationRoute? = null,
    val distanceLeft: Long? = null,
    val errorMessage: String? = null
)
