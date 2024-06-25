package com.epmedu.animeal.feeding.domain.model

data class DomainFeedState(
    val feedPoint: FeedingPoint?,
    val feedingConfirmationState: DomainFeedingConfirmationState = DomainFeedingConfirmationState.Dismissed,
)

sealed interface DomainFeedingConfirmationState {
    data object Dismissed : DomainFeedingConfirmationState
    data object Loading : DomainFeedingConfirmationState
    data object Showing : DomainFeedingConfirmationState
    data object FeedingStarted : DomainFeedingConfirmationState
    data object FeedingWasAlreadyBooked : DomainFeedingConfirmationState
}