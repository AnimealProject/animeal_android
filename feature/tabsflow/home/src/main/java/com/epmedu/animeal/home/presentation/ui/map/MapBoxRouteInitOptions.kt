package com.epmedu.animeal.home.presentation.ui.map

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.mapbox.maps.MapView
import com.mapbox.navigation.ui.maps.route.line.api.MapboxRouteLineApi
import com.mapbox.navigation.ui.maps.route.line.api.MapboxRouteLineView
import com.mapbox.navigation.ui.maps.route.line.model.MapboxRouteLineApiOptions
import com.mapbox.navigation.ui.maps.route.line.model.MapboxRouteLineViewOptions

data class MapBoxRouteInitOptions(
    val routeLineApi: MapboxRouteLineApi,
    val routeLineView: MapboxRouteLineView
)

@Composable
fun rememberMapRouteInitOptions(
    mapView: MapView,
    routeLineViewOptions: MapboxRouteLineViewOptions
) = remember(mapView) {
    MapBoxRouteInitOptions(
        routeLineApi = MapboxRouteLineApi(MapboxRouteLineApiOptions.Builder().build()),
        routeLineView = MapboxRouteLineView(routeLineViewOptions)
    )
}