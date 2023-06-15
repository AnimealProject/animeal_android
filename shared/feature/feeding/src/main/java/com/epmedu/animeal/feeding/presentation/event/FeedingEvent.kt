package com.epmedu.animeal.feeding.presentation.event

import com.epmedu.animeal.feeding.presentation.model.FeedingPhotoItem

sealed interface FeedingEvent {
    data class Start(val id: String) : FeedingEvent
    object Cancel : FeedingEvent
    data class Finish(val feedingPhotos: List<FeedingPhotoItem>) : FeedingEvent
    object Expired : FeedingEvent
    object Reset : FeedingEvent
}