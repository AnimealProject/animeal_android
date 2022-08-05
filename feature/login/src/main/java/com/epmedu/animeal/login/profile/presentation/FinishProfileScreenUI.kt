package com.epmedu.animeal.login.profile.presentation

import android.content.res.Configuration.UI_MODE_NIGHT_YES
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
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.button.AnimealButton
import com.epmedu.animeal.foundation.dialog.AnimealAlertDialog
import com.epmedu.animeal.foundation.input.PhoneNumberInput
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.theme.DisabledButtonColor
import com.epmedu.animeal.foundation.topbar.TopBar
import com.epmedu.animeal.login.profile.ui.BirthDateInput
import com.epmedu.animeal.login.profile.ui.EmailInput
import com.epmedu.animeal.login.profile.ui.NameInput
import com.epmedu.animeal.login.profile.ui.SurnameInput
import com.epmedu.animeal.resources.R

@Composable
internal fun FinishProfileScreenUI(
    state: FinishProfileState,
    onCancel: () -> Unit,
    onEvent: (FinishProfileEvent) -> Unit
) {
    val focusManager = LocalFocusManager.current
    var showCancellationAlert by rememberSaveable { mutableStateOf(false) }

    BackHandler(!showCancellationAlert) {
        showCancellationAlert = true
    }

    if (showCancellationAlert) {
        AnimealAlertDialog(
            title = stringResource(id = R.string.profile_registration_cancel),
            dismissText = stringResource(id = R.string.no),
            acceptText = stringResource(id = R.string.yes),
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
        topBar = {
            TopBar(title = stringResource(id = R.string.profile_title))
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(vertical = 12.dp, horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(text = stringResource(id = R.string.profile_subtitle))

            FinishProfileInputForm(
                state = state,
                focusManager = focusManager,
                onEvent = onEvent
            )

            FinishProfileButtonsRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                onCancelClick = {
                    focusManager.clearFocus()
                    showCancellationAlert = true
                },
                onDoneClick = {
                    focusManager.clearFocus()
                    onEvent(FinishProfileEvent.Submit)
                }
            )
        }
    }
}

@Composable
private fun FinishProfileInputForm(
    state: FinishProfileState,
    focusManager: FocusManager,
    onEvent: (FinishProfileEvent) -> Unit,
) {
    NameInput(
        value = state.name,
        error = state.nameError?.asString(),
        focusManager = focusManager,
        onValueChange = {
            onEvent(FinishProfileEvent.NameChanged(it))
        },
        onFocusRelease = {
            onEvent(FinishProfileEvent.ValidateName)
        }
    )
    SurnameInput(
        value = state.surname,
        error = state.surnameError?.asString(),
        focusManager = focusManager,
        onValueChange = {
            onEvent(FinishProfileEvent.SurnameChanged(it))
        },
        onFocusRelease = {
            onEvent(FinishProfileEvent.ValidateSurname)
        }
    )
    EmailInput(
        value = state.email,
        error = state.emailError?.asString(),
        focusManager = focusManager,
        onValueChange = {
            onEvent(FinishProfileEvent.EmailChanged(it))
        },
        onFocusRelease = {
            onEvent(FinishProfileEvent.ValidateEmail)
        }
    )
    PhoneNumberInput(
        title = stringResource(id = R.string.profile_phone_number),
        value = state.phoneNumber,
        isEnabled = false
    )
    BirthDateInput(
        value = state.birthDate,
        error = state.birthDateError?.asString(),
        initialDate = state.initialDate,
        focusManager = focusManager,
        onValueChange = {
            onEvent(FinishProfileEvent.BirthDateChanged(it))
        },
        onFocusRelease = {
            onEvent(FinishProfileEvent.ValidateBirthDate)
        }
    )
}

@Composable
private fun FinishProfileButtonsRow(
    modifier: Modifier = Modifier,
    onCancelClick: () -> Unit,
    onDoneClick: () -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(48.dp)
    ) {
        AnimealButton(
            modifier = Modifier.weight(1f),
            backgroundColor = DisabledButtonColor,
            contentColor = MaterialTheme.colors.onPrimary,
            text = stringResource(id = R.string.cancel),
            onClick = onCancelClick,
        )
        AnimealButton(
            modifier = Modifier.weight(1f),
            text = stringResource(id = R.string.done),
            onClick = onDoneClick,
        )
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
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