package com.epmedu.animeal.feeding.domain.model

data class DomainFeedState(
    val feedPoint: FeedingPoint? = null,
    val feedingConfirmationState: FeedingConfirmationState = FeedingConfirmationState.Dismissed,
)
