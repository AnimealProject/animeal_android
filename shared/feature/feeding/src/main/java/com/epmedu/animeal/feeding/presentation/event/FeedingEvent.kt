package com.epmedu.animeal.feeding.presentation.event

import com.epmedu.animeal.feeding.presentation.model.FeedingPhotoItem

sealed interface FeedingEvent {
    data class WillFeedClicked(val feedingPointId: String) : FeedingEvent
    object DismissWillFeed : FeedingEvent
    object AgreeWillFeed : FeedingEvent
    object Cancel : FeedingEvent
    data class Finish(val feedingPhotos: List<FeedingPhotoItem>) : FeedingEvent
    object Expired : FeedingEvent
    object Reset : FeedingEvent
}