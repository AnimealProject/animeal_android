package com.epmedu.animeal.feeding.domain.model

sealed interface FeedingConfirmationState {
    data object Dismissed : FeedingConfirmationState
    data object Loading : FeedingConfirmationState
    data class Showing(val isAutoApproved: Boolean) : FeedingConfirmationState
    data object FeedingStarted : FeedingConfirmationState
    data object FeedingWasAlreadyBooked : FeedingConfirmationState
}