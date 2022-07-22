package com.epmedu.animeal.login.profile.presentation

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
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
import com.epmedu.animeal.foundation.button.AnimealShortButton
import com.epmedu.animeal.foundation.dialog.DatePickerDialog
import com.epmedu.animeal.foundation.input.BirthDateInput
import com.epmedu.animeal.foundation.input.PhoneNumberInput
import com.epmedu.animeal.foundation.input.TextInputField
import com.epmedu.animeal.foundation.spacer.HeightSpacer
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.topbar.BackButton
import com.epmedu.animeal.foundation.topbar.TopBar
import com.epmedu.animeal.login.profile.presentation.ui.ProfileEvent
import com.epmedu.animeal.login.profile.presentation.ui.ProfileState
import com.epmedu.animeal.resources.R

@Composable
internal fun FinishProfileScreenUi(
    state: ProfileState,
    onBack: () -> Unit,
    onEvent: (ProfileEvent) -> Unit
) {
    val focusManager = LocalFocusManager.current
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopBar(title = stringResource(id = R.string.profile_title)) {
                BackButton(onClick = onBack)
            }
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
                value = "999112233",
                enabled = false
            )
            BirthDateInputUi(state, focusManager) { onEvent(it) }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                contentAlignment = Alignment.BottomEnd,
            ) {
                AnimealShortButton(
                    text = stringResource(id = R.string.done),
                    onClick = {
                        onEvent(ProfileEvent.Submit)
                        focusManager.clearFocus()
                    },
                    modifier = Modifier.padding(start = 44.dp, bottom = 55.dp)
                )
            }
        }
    }
}

@Composable
private fun FirstNameInputUi(
    state: ProfileState,
    focusManager: FocusManager,
    onEvent: (ProfileEvent) -> Unit
) {
    TextInputField(
        title = stringResource(id = R.string.profile_name_title),
        hint = stringResource(id = R.string.profile_name_hint),
        onValueChange = {
            onEvent(ProfileEvent.FirstnameChanged(it))
        },
        value = state.name,
        errorText = state.nameError?.asString(),
        onClearFocus = {
            if (state.name.isNotBlank()) {
                onEvent(ProfileEvent.FirstnameValidation)
            }
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
    )
}

@Composable
private fun LastNameInputUi(
    state: ProfileState,
    focusManager: FocusManager,
    onEvent: (ProfileEvent) -> Unit
) {
    TextInputField(
        title = stringResource(id = R.string.profile_surname_title),
        hint = stringResource(id = R.string.profile_surname_hint),
        onValueChange = {
            onEvent(ProfileEvent.SurnameChanged(it))
        },
        value = state.surname,
        errorText = state.surnameError?.asString(),
        onClearFocus = {
            if (state.surname.isNotBlank()) {
                onEvent(ProfileEvent.SurnameValidation)
            }
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
    )
}

@Composable
private fun EmailInputUi(
    state: ProfileState,
    focusManager: FocusManager,
    onEvent: (ProfileEvent) -> Unit
) {
    TextInputField(
        title = stringResource(id = R.string.profile_email_title),
        hint = stringResource(id = R.string.profile_email_hint),
        onValueChange = {
            onEvent(ProfileEvent.EmailChanged(it))
        },
        value = state.email,
        errorText = state.emailError?.asString(),
        onClearFocus = {
            if (state.email.isNotBlank()) {
                onEvent(ProfileEvent.EmailValidation)
            }
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
    state: ProfileState,
    focusManager: FocusManager,
    onEvent: (ProfileEvent) -> Unit
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
            onEvent(ProfileEvent.BirthDateChanged(it))
        },
        value = state.birthDateString,
        errorText = state.birthDateError?.asString(),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Number
        ),
        keyboardActions = KeyboardActions(onDone = {
            ProfileEvent.BirthDateValidation
            focusManager.clearFocus()
        })
    )
    DatePickerDialog(
        initialDate = state.initialDate,
        shouldShowDialog = shouldOpenDialog
    ) {
        val formattedDate = formatDateToString(it, DAY_MONTH_YEAR_DOT_FORMATTER)
        onEvent(ProfileEvent.BirthDateChanged(formattedDate.replace(".", "")))
        onEvent(ProfileEvent.BirthDateValidation)
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