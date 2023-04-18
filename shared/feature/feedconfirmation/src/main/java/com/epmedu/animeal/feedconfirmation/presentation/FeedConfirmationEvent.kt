package com.epmedu.animeal.feedconfirmation.presentation

sealed interface FeedConfirmationEvent {
    data class AcceptFeedDialog(
        val feedingPointId: String
    ) : FeedConfirmationEvent
}