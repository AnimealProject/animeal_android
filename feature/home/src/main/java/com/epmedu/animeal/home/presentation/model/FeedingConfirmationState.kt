package com.epmedu.animeal.home.presentation.model

sealed interface FeedingConfirmationState {
    object Dismissed : FeedingConfirmationState
    object Loading : FeedingConfirmationState
    object Showing : FeedingConfirmationState
}