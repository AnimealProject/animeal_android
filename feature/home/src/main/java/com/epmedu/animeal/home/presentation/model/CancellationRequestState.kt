package com.epmedu.animeal.home.presentation.model

sealed interface CancellationRequestState {
    object Dismissed : CancellationRequestState
    object Showing : CancellationRequestState
}