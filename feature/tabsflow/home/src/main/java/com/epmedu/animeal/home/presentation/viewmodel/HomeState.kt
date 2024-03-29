package com.epmedu.animeal.home.presentation.viewmodel

import com.epmedu.animeal.feeding.presentation.model.FeedingPhotoItem
import com.epmedu.animeal.feeding.presentation.model.MapLocation
import com.epmedu.animeal.feeding.presentation.viewmodel.FeedState
import com.epmedu.animeal.feeding.presentation.viewmodel.FeedingPointState
import com.epmedu.animeal.feedings.presentation.model.FeedingsButtonState
import com.epmedu.animeal.geolocation.gpssetting.GpsSettingState
import com.epmedu.animeal.home.presentation.model.CameraState
import com.epmedu.animeal.home.presentation.model.CancellationRequestState
import com.epmedu.animeal.permissions.presentation.PermissionsState
import com.epmedu.animeal.router.presentation.FeedingRouteState
import com.epmedu.animeal.timer.data.model.TimerState

data class HomeState(
    val mapBoxPublicKey: String,
    val mapBoxStyleUri: String,

    val permissionsState: PermissionsState = PermissionsState(),

    val feedingPointState: FeedingPointState = FeedingPointState(),
    val feedingRouteState: FeedingRouteState = FeedingRouteState.Disabled,
    val feedingPhotos: List<FeedingPhotoItem> = emptyList(),
    val locationState: LocationState = LocationState.UndefinedLocation(MapLocation.Tbilisi),
    val feedState: FeedState = FeedState(),

    val feedingsButtonState: FeedingsButtonState = FeedingsButtonState.Hidden,

    /** Current state of gms service. */
    val gpsSettingState: GpsSettingState = GpsSettingState.Disabled,

    val cancellationRequestState: CancellationRequestState = CancellationRequestState.Dismissed,

    val deletePhotoItem: FeedingPhotoItem? = null,

    val cameraState: CameraState = CameraState.Disabled,

    val timerState: TimerState? = null,

    val isError: Boolean = false
)

sealed interface LocationState {
    val location: MapLocation
    data class UndefinedLocation(override val location: MapLocation) : LocationState
    data class InitialLocation(override val location: MapLocation) : LocationState
    data class ExactLocation(override val location: MapLocation) : LocationState
}