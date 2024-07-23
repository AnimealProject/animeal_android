package com.epmedu.animeal.home.presentation.model

sealed interface CancellationRequestState {
    data object Dismissed : CancellationRequestState
    data object Showing : CancellationRequestState
}