package com.epmedu.animeal.feeding.presentation.viewmodel

sealed interface FeedingConfirmationState {
    data object Dismissed : FeedingConfirmationState
    data object Loading : FeedingConfirmationState
    data object Showing : FeedingConfirmationState
    data object FeedingStarted : FeedingConfirmationState
    data object FeedingWasAlreadyBooked : FeedingConfirmationState
}