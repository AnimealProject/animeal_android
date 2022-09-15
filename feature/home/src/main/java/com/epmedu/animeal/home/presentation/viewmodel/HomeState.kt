package com.epmedu.animeal.home.presentation.viewmodel

import com.epmedu.animeal.common.component.GpsSettingsProvider
import com.epmedu.animeal.common.data.model.MapLocation
import com.epmedu.animeal.home.data.model.FeedingPoint
import com.mapbox.maps.Style

data class HomeState(
    val currentLocation: MapLocation = MapLocation(),
    val currentFeedingPoint: FeedingPoint? = null,
    val mapBoxPublicKey: String = "",
    val mapBoxStyleUri: String = Style.MAPBOX_STREETS,
    val gpsSettingState: GpsSettingsProvider.GpsSettingState = GpsSettingsProvider.GpsSettingState.Disabled,
)