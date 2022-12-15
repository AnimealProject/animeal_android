package com.epmedu.animeal.home.presentation.model

import com.mapbox.navigation.base.route.NavigationRoute

data class RouteResult(
    val isSuccessful: Boolean,
    val timeLeft: String? = null,
    val routeData: NavigationRoute? = null,
    val distanceLeft: String? = null,
    val errorMessage: String? = null
)
