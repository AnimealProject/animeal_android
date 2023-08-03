package com.epmedu.animeal.feeding.presentation.viewmodel

sealed interface FeedingConfirmationState {
    object Dismissed : FeedingConfirmationState
    object Loading : FeedingConfirmationState
    object Showing : FeedingConfirmationState
    object FeedingStarted : FeedingConfirmationState
    object FeedingWasAlreadyBooked : FeedingConfirmationState
}