package com.epmedu.animeal.home.presentation

import android.net.Uri
import com.epmedu.animeal.feeding.presentation.model.FeedingPhotoItem

sealed interface HomeScreenEvent {

    sealed interface TimerCancellationEvent : HomeScreenEvent {
        data object CancellationAttempt : TimerCancellationEvent
        data object CancellationAccepted : TimerCancellationEvent
        data object CancellationDismissed : TimerCancellationEvent
    }

    data object DismissThankYouEvent : HomeScreenEvent

    sealed interface CameraEvent : HomeScreenEvent {
        data object OpenCamera : CameraEvent
        data class TakeNewPhoto(val uri: Uri) : CameraEvent
        data object CloseCamera : CameraEvent
    }

    sealed interface FeedingGalleryEvent : HomeScreenEvent {
        data class DeletePhoto(val photo: FeedingPhotoItem) : FeedingGalleryEvent
        data class ConfirmDeletePhoto(val photo: FeedingPhotoItem) : FeedingGalleryEvent
        data object CloseDeletePhotoDialog : FeedingGalleryEvent
    }

    data object ErrorShowed : HomeScreenEvent
    data object ScreenCreated : HomeScreenEvent
    data object ScreenDisplayed : HomeScreenEvent
    data object InitialLocationWasDisplayed : HomeScreenEvent
}