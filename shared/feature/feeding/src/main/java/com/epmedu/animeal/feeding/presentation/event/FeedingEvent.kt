package com.epmedu.animeal.feeding.presentation.event

import com.epmedu.animeal.feeding.presentation.model.FeedingPhotoItem

sealed interface FeedingEvent {
    data class Start(val id: String) : FeedingEvent
    data object Cancel : FeedingEvent
    data class Finish(val feedingPhotos: List<FeedingPhotoItem>) : FeedingEvent
    data object Expired : FeedingEvent
    data object Reset : FeedingEvent
}