@file:OptIn(ExperimentalMaterialApi::class)

package com.epmedu.animeal.signup.finishprofile.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.profile.presentation.ProfileInputFormEvent
import com.epmedu.animeal.profile.presentation.ui.RegionsLazyColumn
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
    val scope = rememberCoroutineScope()

    var showCancellationAlert by rememberSaveable { mutableStateOf(false) }

    BackHandler(!showCancellationAlert) {
        showCancellationAlert = true
    }

    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

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

    ModalBottomSheetLayout(
        scrimColor = Color.Transparent,
        sheetState = bottomSheetState,
        sheetContent = {
            RegionsLazyColumn(scope, bottomSheetState) { region ->
                onInputFormEvent(ProfileInputFormEvent.RegionChanged(region))
            }
        },
    ) {
        FinishProfileContent(
            state = state,
            bottomSheetState = bottomSheetState,
            focusRequester = focusRequester,
            onCancel = { showCancellationAlert = true },
            onDone = onDone,
            onInputFormEvent = onInputFormEvent
        )
    }
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