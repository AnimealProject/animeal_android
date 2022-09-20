package com.epmedu.animeal.signup.finishprofile.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.topbar.TopBar
import com.epmedu.animeal.resources.R
import com.epmedu.animeal.signup.finishprofile.presentation.FinishProfileScreenEvent.Submit
import com.epmedu.animeal.signup.finishprofile.presentation.ui.CancellationDialog
import com.epmedu.animeal.signup.finishprofile.presentation.ui.FinishProfileButtonsRow
import com.epmedu.animeal.signup.finishprofile.presentation.ui.FinishProfileInputForm
import com.epmedu.animeal.signup.finishprofile.presentation.viewmodel.FinishProfileState

@Composable
internal fun FinishProfileScreenUI(
    state: FinishProfileState,
    onCancel: () -> Unit,
    onEvent: (FinishProfileScreenEvent) -> Unit
) {
    val focusManager = LocalFocusManager.current
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

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        topBar = { TopBar(title = stringResource(R.string.profile_title)) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 24.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
                Text(
                    modifier = Modifier.padding(top = 12.dp),
                    text = stringResource(R.string.profile_subtitle),
                    style = MaterialTheme.typography.subtitle1,
                )
                FinishProfileInputForm(
                    state = state,
                    focusManager = focusManager,
                    onEvent = onEvent
                )
            }
            FinishProfileButtonsRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                onCancelClick = {
                    focusManager.clearFocus()
                    showCancellationAlert = true
                },
                onDoneClick = {
                    focusManager.clearFocus()
                    onEvent(Submit)
                }
            )
        }
    }
}

@AnimealPreview
@Composable
private fun FinishProfileScreenPreview() {
    AnimealTheme {
        FinishProfileScreenUI(
            state = FinishProfileState(),
            onCancel = {},
            onEvent = {},
        )
    }
}