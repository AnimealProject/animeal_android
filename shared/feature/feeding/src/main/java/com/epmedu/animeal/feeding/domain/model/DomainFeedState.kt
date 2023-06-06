package com.epmedu.animeal.feeding.domain.model

data class DomainFeedState(
    val feedPoint: FeedingPoint?,
    val feedingConfirmationState: DomainFeedingConfirmationState = DomainFeedingConfirmationState.Dismissed,
)

enum class DomainFeedingConfirmationState {
    Dismissed, Loading, Showing, FeedingStarted
}