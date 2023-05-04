package com.epmedu.animeal.feeding.presentation.event

sealed interface FeedingEvent {
    object Start : FeedingEvent
    object Cancel : FeedingEvent
    object Finish : FeedingEvent
    object Expired : FeedingEvent
}