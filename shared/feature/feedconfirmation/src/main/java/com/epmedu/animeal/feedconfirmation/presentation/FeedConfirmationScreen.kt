package com.epmedu.animeal.feedconfirmation.presentation

import androidx.compose.runtime.Composable

@Composable
fun FeedConfirmationScreen(
    onAgreeClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    FeedConfirmationUI(onAgreeClick, onCancelClick)
}