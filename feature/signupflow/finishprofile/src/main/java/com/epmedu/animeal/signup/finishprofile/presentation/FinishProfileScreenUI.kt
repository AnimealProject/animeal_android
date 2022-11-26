package com.epmedu.animeal.signup.finishprofile.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusRequester
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.profile.presentation.ProfileInputFormEvent
import com.epmedu.animeal.profile.presentation.viewmodel.ProfileState
import com.epmedu.animeal.signup.finishprofile.presentation.ui.CancellationDialog
import com.epmedu.animeal.signup.finishprofile.presentation.ui.FinishProfileContent

@Composable
internal fun FinishProfileScreenUI(
    state: ProfileState,
    focusRequester: FocusRequester,
    onCancel: () -> Unit,
    onDone: () -> Unit,
    onInputFormEvent: (ProfileInputFormEvent) -> Unit
) {
    var showCancellationAlert by rememberSaveable { mutableStateOf(false) }

    BackHandler(!showCancellationAlert) {
        showCancellationAlert = true
    }

    if (showCancellationAlert) {
        CancellationDialog(
            onDismissRequest = { showCancellationAlert = false },
            onDismiss = { showCancellationAlert = false },
            onConfirm = {
                showCancellationAlert = false
                onCancel()
            }
        )
    }

    FinishProfileContent(
        state = state,
        focusRequester = focusRequester,
        onCancel = { showCancellationAlert = true },
        onDone = onDone,
        onInputFormEvent = onInputFormEvent
    )
}

@AnimealPreview
@Composable
private fun FinishProfileScreenPreview() {
    AnimealTheme {
        FinishProfileScreenUI(
            state = ProfileState(),
            focusRequester = FocusRequester(),
            onCancel = {},
            onDone = {},
            onInputFormEvent = {}
        )
    }
}