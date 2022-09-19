package com.epmedu.animeal.home.presentation.viewmodel

import com.epmedu.animeal.common.data.model.MapLocation
import com.epmedu.animeal.home.data.model.FeedSpot
import com.mapbox.maps.Style

data class HomeState(
    val currentLocation: MapLocation = MapLocation(),
    val currentFeedSpot: FeedSpot? = null,
    val mapBoxPublicKey: String = "",
    val mapBoxStyleUri: String = Style.MAPBOX_STREETS
)