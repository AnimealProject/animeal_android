package com.epmedu.animeal.home.presentation.ui.map

import com.mapbox.navigation.ui.maps.route.line.api.MapboxRouteLineApi
import com.mapbox.navigation.ui.maps.route.line.api.MapboxRouteLineView
import com.mapbox.navigation.ui.maps.route.line.model.MapboxRouteLineOptions

data class MapBoxRouteInitOptions(
    val routeLineOptions: MapboxRouteLineOptions,
    val routeLineApi: MapboxRouteLineApi = MapboxRouteLineApi(routeLineOptions),
    val routeLineView: MapboxRouteLineView = MapboxRouteLineView(routeLineOptions)
)