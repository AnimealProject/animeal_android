package com.epmedu.animeal.feeding.presentation.viewmodel

data class WillFeedState(
    val feedConfirmationDialog: FeedConfirmationDialogState = FeedConfirmationDialogState.Dismissed,
    val showMotivateUseGpsDialog: Boolean = false,
    val openGpsSettings: Boolean = false,
    val showLocationSettingsEmbeddedDialog: Boolean = false
)

sealed interface FeedConfirmationDialogState {
    object Dismissed : FeedConfirmationDialogState
    object Showing : FeedConfirmationDialogState
}