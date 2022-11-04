package com.epmedu.animeal.home.presentation.viewmodel

import com.epmedu.animeal.extensions.StableList
import com.epmedu.animeal.home.data.model.FeedingPoint
import com.epmedu.animeal.home.presentation.model.FeedingPointUi
import com.epmedu.animeal.home.presentation.model.FeedingRouteState
import com.epmedu.animeal.home.presentation.model.GpsSettingState
import com.epmedu.animeal.home.presentation.model.MapLocation
import com.mapbox.maps.Style

data class HomeState(
    val currentLocation: MapLocation = MapLocation.Tbilisi,
    val currentFeedingPoint: FeedingPoint? = null,
    val mapBoxPublicKey: String = "",
    val mapBoxStyleUri: String = Style.MAPBOX_STREETS,
    val gpsSettingState: GpsSettingState = GpsSettingState.Disabled,
    val feedingPoints: StableList<FeedingPointUi> = StableList(emptyList()),
    val feedingRouteState: FeedingRouteState = FeedingRouteState()
)
