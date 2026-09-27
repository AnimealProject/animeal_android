package com.epmedu.animeal.messages.presentation.ui

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.epmedu.animeal.messages.presentation.event.MessageCreateDialogEvent.Close
import com.epmedu.animeal.messages.presentation.model.FeedingPointModel
import com.epmedu.animeal.messages.presentation.viewmodel.MessageCreateDialogState.FormState.Closed
import com.epmedu.animeal.messages.presentation.viewmodel.MessageCreateDialogState.FormState.Confirmed
import com.epmedu.animeal.messages.presentation.viewmodel.MessageCreateDialogState.FormState.Opened
import com.epmedu.animeal.messages.presentation.viewmodel.MessageCreateDialogViewModel

@Composable
fun MessageCreateDialog(
    feedingPointCode: String,
    feedingPointTitle: String,
    onConfirm: (body: String) -> Unit
) {
    val viewModel: MessageCreateDialogViewModel = hiltViewModel()
    val state by viewModel.stateFlow.collectAsState()

    BackHandler(enabled = state.formState != Closed) { /* Disable parent back handler */ }

    when (state.formState) {
        Opened -> {
            MessageCreateDialogControl(
                feedingPoint = FeedingPointModel(feedingPointCode, feedingPointTitle),
                state = state,
                onEvent = viewModel::handleEvent,
            )
        }

        Confirmed -> {
            onConfirm(viewModel.state.body)
            viewModel.handleEvent(Close)
        }

        Closed -> {}
    }
}