package com.epmedu.animeal.home.presentation.viewmodel

import com.epmedu.animeal.extensions.StableList
import com.epmedu.animeal.feeding.domain.model.FeedingPoint
import com.epmedu.animeal.feeding.presentation.model.FeedingPointModel
import com.epmedu.animeal.feeding.presentation.model.MapLocation
import com.epmedu.animeal.home.presentation.model.GpsSettingState
import com.epmedu.animeal.home.presentation.model.WillFeedState
import com.mapbox.maps.Style

data class HomeState(
    val currentLocation: MapLocation = MapLocation.Tbilisi,
    val currentFeedingPoint: FeedingPoint? = null,
    val mapBoxPublicKey: String = "",
    val mapBoxStyleUri: String = Style.MAPBOX_STREETS,
    val gpsSettingState: GpsSettingState = GpsSettingState.Disabled,
    val feedingPoints: StableList<FeedingPointModel> = StableList(emptyList()),
    val willFeedState: WillFeedState = WillFeedState(),
    val isInitialGeolocationPermissionAsked: Boolean = false,
)
