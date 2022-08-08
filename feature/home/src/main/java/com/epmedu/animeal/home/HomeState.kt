package com.epmedu.animeal.home

import com.epmedu.animeal.common.data.model.MapLocation
import com.mapbox.maps.Style

data class HomeState(
    val currentLocation: MapLocation = MapLocation(),
    val mapBoxPublicKey: String = "",
    val mapBoxStyleUri: String = Style.MAPBOX_STREETS
)