package com.epmedu.animeal.feeding.presentation.event

import com.epmedu.animeal.feeding.presentation.model.FeedingPhotoItem

sealed interface FeedingEvent {
    object Start : FeedingEvent
    object Cancel : FeedingEvent
    data class Finish(val feedingPhotos: List<FeedingPhotoItem>) : FeedingEvent
    object Expired : FeedingEvent
}