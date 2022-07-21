package com.epmedu.animeal.login.profile.presentation

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.epmedu.animeal.resources.R

@Suppress("LongMethod")
@Composable
internal fun FinishProfileScreenUi(
    viewModel: FinishProfileViewModel,
    onBack: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val state = viewModel.stateFlow.collectAsState().value
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
            TextInputField(
                title = stringResource(id = R.string.profile_name_title),
                hint = stringResource(id = R.string.profile_name_hint),
                onValueChange = {
                    viewModel.onEvent(ProfileEvent.FirstnameChanged(it))
                },
                value = state.name,
                errorText = state.nameError?.asString(),
                onClearFocus = {
                    if (state.name.isNotBlank()) {
                        viewModel.onEvent(ProfileEvent.FirstnameValidation)
                    }
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
            )
            TextInputField(
                title = stringResource(id = R.string.profile_surname_title),
                hint = stringResource(id = R.string.profile_surname_hint),
                onValueChange = {
                    viewModel.onEvent(ProfileEvent.SurnameChanged(it))
                },
                value = state.surname,
                errorText = state.surnameError?.asString(),
                onClearFocus = {
                    if (state.surname.isNotBlank()) {
                        viewModel.onEvent(ProfileEvent.SurnameValidation)
                    }
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
            )
            TextInputField(
                title = stringResource(id = R.string.profile_email_title),
                hint = stringResource(id = R.string.profile_email_hint),
                onValueChange = {
                    viewModel.onEvent(ProfileEvent.EmailChanged(it))
                },
                value = state.email,
                errorText = state.emailError?.asString(),
                onClearFocus = {
                    if (state.email.isNotBlank()) {
                        viewModel.onEvent(ProfileEvent.EmailValidation)
                    }
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Email
                ),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
            )
            PhoneNumberInput(
                title = stringResource(id = R.string.profile_phone_number),
                value = "999112233",
                enabled = false
            )
            BirthDateInputUi(viewModel, focusManager)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                contentAlignment = Alignment.BottomEnd,
            ) {
                AnimealShortButton(
                    text = stringResource(id = R.string.done),
                    onClick = {
                        viewModel.onEvent(ProfileEvent.Submit)
                        focusManager.clearFocus()
                    },
                    modifier = Modifier.padding(start = 44.dp, bottom = 55.dp)
                )
            }
        }
    }
}

@Composable
private fun BirthDateInputUi(viewModel: FinishProfileViewModel, focusManager: FocusManager) {
    val state = viewModel.stateFlow.collectAsState().value
    var shouldOpenDialog by remember { mutableStateOf(false) }
    BirthDateInput(
        title = stringResource(id = R.string.profile_birth_date),
        isEnabled = true,
        onIconClick = {
            focusManager.clearFocus()
            shouldOpenDialog = true
        },
        onValueChange = {
            viewModel.onEvent(ProfileEvent.BirthDateChanged(it))
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
        initialDate = viewModel.stateFlow.collectAsState().value.initialDate,
        shouldShowDialog = shouldOpenDialog
    ) {
        val formattedDate = formatDateToString(it, DAY_MONTH_YEAR_DOT_FORMATTER)
        viewModel.onEvent(ProfileEvent.BirthDateChanged(formattedDate.replace(".", "")))
        viewModel.onEvent(ProfileEvent.BirthDateValidation)
    }
}

@Preview
@Composable
private fun EnterPhoneScreenPreview() {
    AnimealTheme {
        FinishProfileScreen()
    }
}