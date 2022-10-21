package com.epmedu.animeal.willfeed.presentation

import androidx.compose.runtime.Composable

@Composable
fun FeedConfirmationScreen(
    onAgreeClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    FeedConfirmationUI(onAgreeClick, onCancelClick)
}