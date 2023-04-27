package com.epmedu.animeal.home.presentation

import android.net.Uri
import com.epmedu.animeal.feeding.presentation.model.FeedingPointModel
import com.epmedu.animeal.feeding.presentation.ui.FeedingPhotoItem
import com.epmedu.animeal.foundation.tabs.model.AnimalType
import com.epmedu.animeal.home.domain.PermissionStatus
import com.epmedu.animeal.home.presentation.model.RouteResult

sealed interface HomeScreenEvent {

    sealed interface FeedingPointEvent : HomeScreenEvent {
        data class Select(val feedingPoint: FeedingPointModel) : FeedingPointEvent
        data class FavouriteChange(val isFavourite: Boolean) : FeedingPointEvent
        data class AnimalTypeChange(val type: AnimalType) : FeedingPointEvent
    }

    sealed interface FeedingEvent : HomeScreenEvent {
        object Start : FeedingEvent
        object Cancel : FeedingEvent
        object Finish : FeedingEvent
        object Expired : FeedingEvent
    }

    sealed interface RouteEvent : HomeScreenEvent {
        data class FeedingRouteUpdateRequest(val result: RouteResult) : RouteEvent
        data class FeedingTimerUpdateRequest(val timeLeft: Long) : RouteEvent
    }

    sealed interface WillFeedEvent : HomeScreenEvent {
        object ShowWillFeedDialog : WillFeedEvent
        object DismissWillFeedDialog : WillFeedEvent
    }

    sealed interface TimerEvent : HomeScreenEvent {
        object Disable : TimerEvent
    }

    data class GeolocationPermissionStatusChanged(val status: PermissionStatus) : HomeScreenEvent
    object GeolocationPermissionAsked : HomeScreenEvent
    data class CameraPermissionStatusChanged(val status: PermissionStatus) : HomeScreenEvent
    object CameraPermissionAsked : HomeScreenEvent

    sealed interface TimerCancellationEvent : HomeScreenEvent {
        object CancellationAttempt : TimerCancellationEvent
        object CancellationAccepted : TimerCancellationEvent
        object CancellationDismissed : TimerCancellationEvent
    }

    sealed interface CameraEvent : HomeScreenEvent {
        object OpenCamera : CameraEvent
        data class TakeNewPhoto(val uri: Uri) : CameraEvent
        object CloseCamera : CameraEvent
    }

    sealed interface FeedingGalleryEvent : HomeScreenEvent {
        data class DeletePhoto(val photo: FeedingPhotoItem) : FeedingGalleryEvent
        data class ConfirmDeletePhoto(val photo: FeedingPhotoItem) : FeedingGalleryEvent
        object CloseDeletePhotoDialog : FeedingGalleryEvent
    }

    object ErrorShowed : HomeScreenEvent
    object ScreenDisplayed : HomeScreenEvent
    object MapInteracted : HomeScreenEvent
}