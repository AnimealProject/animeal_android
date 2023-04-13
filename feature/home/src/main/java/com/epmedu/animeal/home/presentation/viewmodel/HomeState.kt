package com.epmedu.animeal.home.presentation.viewmodel

import android.net.Uri
import com.epmedu.animeal.feeding.presentation.model.FeedingPointModel
import com.epmedu.animeal.feeding.presentation.model.MapLocation
import com.epmedu.animeal.home.domain.PermissionStatus
import com.epmedu.animeal.home.presentation.model.CancellationRequestState
import com.epmedu.animeal.home.presentation.model.FeedingRouteState
import com.epmedu.animeal.home.presentation.model.GpsSettingState
import com.epmedu.animeal.home.presentation.model.WillFeedState
import com.epmedu.animeal.timer.data.model.TimerState
import com.mapbox.maps.Style
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class HomeState(
    val currentFeedingPoint: FeedingPointModel? = null,
    val feedingPoints: ImmutableList<FeedingPointModel> = persistentListOf(),
    val feedingRouteState: FeedingRouteState = FeedingRouteState.Disabled,
    val feedingPhotos: List<Uri> = emptyList(),
    val willFeedState: WillFeedState = WillFeedState.Dismissed,
    val mapBoxPublicKey: String = "",
    val mapBoxStyleUri: String = Style.MAPBOX_STREETS,

    val locationState: LocationState = LocationState.UndefinedLocation(MapLocation.Tbilisi),

    /** Current state of gms service */
    val gpsSettingState: GpsSettingState = GpsSettingState.Disabled,
    val geolocationPermissionStatus: PermissionStatus = PermissionStatus.Restricted,

    val cameraPermissionStatus: PermissionStatus = PermissionStatus.Restricted,

    val isInitialGeolocationPermissionAsked: Boolean = false,
    val isCameraPermissionAsked: Boolean = false,

    val cancellationRequestState: CancellationRequestState = CancellationRequestState.Dismissed,

    val timerState: TimerState? = null,

    val isError: Boolean = false
)

sealed interface LocationState {
    val location: MapLocation

    val isUndefined: Boolean get() = false
    val isInitial: Boolean get() = false
    val isExact: Boolean get() = false

    data class UndefinedLocation(override val location: MapLocation) : LocationState {
        override val isUndefined: Boolean get() = true
    }

    data class InitialLocation(override val location: MapLocation) : LocationState {
        override val isInitial: Boolean get() = true
    }

    data class ExactLocation(override val location: MapLocation) : LocationState {
        override val isExact: Boolean get() = true
    }
}