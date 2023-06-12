package com.epmedu.animeal.feeding.presentation.viewmodel

import com.epmedu.animeal.common.constants.DefaultConstants.EMPTY_STRING

interface FeedingConfirmationState {
    companion object {
        const val REQUEST_CANCELLED = "RequestCancelled"
    }
    data class Dismissed(val reason: String = EMPTY_STRING) : FeedingConfirmationState
    object Loading : FeedingConfirmationState
    object Showing : FeedingConfirmationState
    object FeedingStarted : FeedingConfirmationState
}