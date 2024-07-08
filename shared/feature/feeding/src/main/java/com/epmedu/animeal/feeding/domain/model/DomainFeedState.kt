package com.epmedu.animeal.feeding.domain.model

data class DomainFeedState(
    val feedPoint: FeedingPoint? = null,
    val feedingConfirmationState: DomainFeedingConfirmationState = DomainFeedingConfirmationState.Dismissed,
)

sealed interface DomainFeedingConfirmationState {
    object Dismissed : DomainFeedingConfirmationState
    object Loading : DomainFeedingConfirmationState
    object Showing : DomainFeedingConfirmationState
    object FeedingStarted : DomainFeedingConfirmationState
    object FeedingWasAlreadyBooked : DomainFeedingConfirmationState
}