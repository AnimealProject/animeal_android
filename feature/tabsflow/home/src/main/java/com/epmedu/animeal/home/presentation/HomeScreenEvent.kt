package com.epmedu.animeal.home.presentation

import android.net.Uri
import com.epmedu.animeal.feeding.presentation.model.FeedingPhotoItem

sealed interface HomeScreenEvent {

    sealed interface TimerCancellationEvent : HomeScreenEvent {
        object CancellationAttempt : TimerCancellationEvent
        object CancellationAccepted : TimerCancellationEvent
        object CancellationDismissed : TimerCancellationEvent
    }

    object DismissThankYouEvent : HomeScreenEvent

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
    object InitialLocationWasDisplayed : HomeScreenEvent
}