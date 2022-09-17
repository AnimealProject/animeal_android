package com.epmedu.animeal.signup.finishprofile.presentation

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.tooling.preview.Preview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.profile.presentation.ProfileInputFormEvent
import com.epmedu.animeal.profile.presentation.viewmodel.ProfileState
import com.epmedu.animeal.signup.finishprofile.presentation.ui.CancellationDialog
import com.epmedu.animeal.signup.finishprofile.presentation.ui.FinishProfileContent

@Composable
internal fun FinishProfileScreenUI(
    state: ProfileState,
    focusRequester: FocusRequester,
    onBack: () -> Unit,
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
                onBack()
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

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun FinishProfileScreenPreview() {
    AnimealTheme {
        FinishProfileScreenUI(
            state = ProfileState(),
            focusRequester = FocusRequester(),
            onBack = {},
            onDone = {},
            onInputFormEvent = {}
        )
    }
}