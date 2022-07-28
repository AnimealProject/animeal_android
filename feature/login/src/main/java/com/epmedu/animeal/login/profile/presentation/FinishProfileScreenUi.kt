package com.epmedu.animeal.login.profile.presentation

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.extensions.DAY_MONTH_YEAR_DOT_FORMATTER
import com.epmedu.animeal.extensions.formatDateToString
import com.epmedu.animeal.foundation.button.AnimealButton
import com.epmedu.animeal.foundation.dialog.AnimealAlertDialog
import com.epmedu.animeal.foundation.dialog.DatePickerDialog
import com.epmedu.animeal.foundation.input.BirthDateInput
import com.epmedu.animeal.foundation.input.PhoneNumberInput
import com.epmedu.animeal.foundation.input.TextInputField
import com.epmedu.animeal.foundation.spacer.HeightSpacer
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.topbar.TopBar
import com.epmedu.animeal.resources.R

@Composable
internal fun FinishProfileScreenUi(
    state: FinishProfileState,
    onCancel: () -> Unit,
    onEvent: (FinishProfileEvent) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val showCancellationAlert = rememberSaveable { mutableStateOf(false) }

    if (showCancellationAlert.value) {
        AnimealAlertDialog(
            title = stringResource(id = R.string.profile_registration_cancel), // TBD
            dismissText = stringResource(id = R.string.no),
            acceptText = stringResource(id = R.string.yes),
            onDismiss = {
                showCancellationAlert.value = false
            },
            onConfirm = {
                showCancellationAlert.value = false
                onCancel()
            }
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(title = stringResource(id = R.string.profile_title))
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            HeightSpacer(12.dp)
            Text(text = stringResource(id = R.string.profile_subtitle))
            FirstNameInputUi(
                state = state,
                focusManager = focusManager,
                onEvent = onEvent
            )
            LastNameInputUi(
                state = state,
                focusManager = focusManager,
                onEvent = onEvent
            )
            EmailInputUi(
                state = state,
                focusManager = focusManager,
                onEvent = onEvent
            )
            PhoneNumberInput(
                title = stringResource(id = R.string.profile_phone_number),
                value = state.phoneNumber,
                enabled = false
            )
            BirthDateInputUi(
                state = state,
                focusManager = focusManager,
                onEvent = onEvent
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                horizontalArrangement = Arrangement.spacedBy(48.dp)
            ) {
                AnimealButton(
                    modifier = Modifier.weight(1f),
                    text = stringResource(id = R.string.cancel),
                    onClick = {
                        focusManager.clearFocus()

                        showCancellationAlert.value = true
                    },
                )

                AnimealButton(
                    modifier = Modifier.weight(1f),
                    text = stringResource(id = R.string.done),
                    onClick = {
                        focusManager.clearFocus()
                        onEvent(FinishProfileEvent.Submit)
                    },
                )
            }
        }
    }
}

@Composable
private fun FirstNameInputUi(
    state: FinishProfileState,
    focusManager: FocusManager,
    onEvent: (FinishProfileEvent) -> Unit
) {
    TextInputField(
        title = stringResource(id = R.string.profile_name_title),
        hint = stringResource(id = R.string.profile_name_hint),
        onValueChange = {
            onEvent(FinishProfileEvent.NameChanged(it))
        },
        value = state.name,
        errorText = state.nameError?.asString(),
        onClearFocus = {
            onEvent(FinishProfileEvent.ValidateName)
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
    )
}

@Composable
private fun LastNameInputUi(
    state: FinishProfileState,
    focusManager: FocusManager,
    onEvent: (FinishProfileEvent) -> Unit
) {
    TextInputField(
        title = stringResource(id = R.string.profile_surname_title),
        hint = stringResource(id = R.string.profile_surname_hint),
        onValueChange = {
            onEvent(FinishProfileEvent.SurnameChanged(it))
        },
        value = state.surname,
        errorText = state.surnameError?.asString(),
        onClearFocus = {
            onEvent(FinishProfileEvent.ValidateSurname)
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
    )
}

@Composable
private fun EmailInputUi(
    state: FinishProfileState,
    focusManager: FocusManager,
    onEvent: (FinishProfileEvent) -> Unit
) {
    TextInputField(
        title = stringResource(id = R.string.profile_email_title),
        hint = stringResource(id = R.string.profile_email_hint),
        onValueChange = {
            onEvent(FinishProfileEvent.EmailChanged(it))
        },
        value = state.email,
        errorText = state.emailError?.asString(),
        onClearFocus = {
            onEvent(FinishProfileEvent.ValidateEmail)
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Email
        ),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
    )
}

@Composable
private fun BirthDateInputUi(
    state: FinishProfileState,
    focusManager: FocusManager,
    onEvent: (FinishProfileEvent) -> Unit
) {
    val shouldOpenDialog = remember { mutableStateOf(false) }
    BirthDateInput(
        title = stringResource(id = R.string.profile_birth_date),
        isEnabled = true,
        onIconClick = {
            focusManager.clearFocus()
            shouldOpenDialog.value = true
        },
        onValueChange = {
            onEvent(FinishProfileEvent.BirthDateChanged(it))
        },
        value = state.birthDateString,
        errorText = state.birthDateError?.asString(),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Number
        ),
        keyboardActions = KeyboardActions(onDone = {
            FinishProfileEvent.ValidateBirthDate
            focusManager.clearFocus()
        })
    )
    DatePickerDialog(
        initialDate = state.initialDate,
        shouldShowDialog = shouldOpenDialog
    ) {
        val formattedDate = formatDateToString(it, DAY_MONTH_YEAR_DOT_FORMATTER)
        onEvent(FinishProfileEvent.BirthDateChanged(formattedDate.replace(".", "")))
        onEvent(FinishProfileEvent.ValidateBirthDate)
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun EnterPhoneScreenPreview() {
    AnimealTheme {
        FinishProfileScreen()
    }
}