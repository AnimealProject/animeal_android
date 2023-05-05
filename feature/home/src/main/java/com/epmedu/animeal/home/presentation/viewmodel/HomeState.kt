package com.epmedu.animeal.home.presentation.viewmodel

import com.epmedu.animeal.feeding.presentation.model.FeedingPhotoItem
import com.epmedu.animeal.feeding.presentation.model.MapLocation
import com.epmedu.animeal.feeding.presentation.viewmodel.FeedingPointState
import com.epmedu.animeal.feeding.presentation.viewmodel.WillFeedState
import com.epmedu.animeal.home.domain.PermissionStatus
import com.epmedu.animeal.home.presentation.model.CameraState
import com.epmedu.animeal.home.presentation.model.CancellationRequestState
import com.epmedu.animeal.home.presentation.model.GpsSettingState
import com.epmedu.animeal.timer.data.model.TimerState
import com.mapbox.maps.Style

data class HomeState(
    val feedingPointState: FeedingPointState = FeedingPointState(),
    val feedingPhotos: List<FeedingPhotoItem> = emptyList(),
    val locationState: LocationState = LocationState.UndefinedLocation(MapLocation.Tbilisi),
    val willFeedState: WillFeedState = WillFeedState.Dismissed,

    val mapBoxPublicKey: String = "",
    val mapBoxStyleUri: String = Style.MAPBOX_STREETS,

    /** Current state of gms service */
    val gpsSettingState: GpsSettingState = GpsSettingState.Disabled,
    val geolocationPermissionStatus: PermissionStatus = PermissionStatus.Restricted,

    val cameraPermissionStatus: PermissionStatus = PermissionStatus.Restricted,

    val isInitialGeolocationPermissionAsked: Boolean = false,
    val isCameraPermissionAsked: Boolean = false,

    val cancellationRequestState: CancellationRequestState = CancellationRequestState.Dismissed,

    val deletePhotoItem: FeedingPhotoItem? = null,

    val cameraState: CameraState = CameraState.Disabled,

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