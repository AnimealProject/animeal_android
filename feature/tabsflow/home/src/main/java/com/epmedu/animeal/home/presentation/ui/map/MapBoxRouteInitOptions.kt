package com.epmedu.animeal.home.presentation.ui.map

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.mapbox.maps.MapView
import com.mapbox.navigation.ui.maps.route.line.api.MapboxRouteLineApi
import com.mapbox.navigation.ui.maps.route.line.api.MapboxRouteLineView
import com.mapbox.navigation.ui.maps.route.line.model.MapboxRouteLineOptions

data class MapBoxRouteInitOptions(
    val routeLineOptions: MapboxRouteLineOptions,
    val routeLineApi: MapboxRouteLineApi = MapboxRouteLineApi(routeLineOptions),
    val routeLineView: MapboxRouteLineView = MapboxRouteLineView(routeLineOptions)
)

@Composable
fun rememberMapRouteInitOptions(
    mapView: MapView,
    mapBoxNavigationInitOptions: MapBoxRouteInitOptions
) = remember(mapView) { mapBoxNavigationInitOptions }