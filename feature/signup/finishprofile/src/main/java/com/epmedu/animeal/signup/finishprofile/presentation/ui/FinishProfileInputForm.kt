package com.epmedu.animeal.signup.finishprofile.presentation.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.input.PhoneNumberInput
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.resources.R
import com.epmedu.animeal.signup.finishprofile.presentation.FinishProfileScreenEvent
import com.epmedu.animeal.signup.finishprofile.presentation.viewmodel.FinishProfileState

@Composable
internal fun FinishProfileInputForm(
    state: FinishProfileState,
    focusManager: FocusManager,
    onEvent: (FinishProfileScreenEvent) -> Unit,
) {
    NameInput(
        value = state.name,
        error = state.nameError?.asString(),
        focusManager = focusManager,
        onValueChange = {
            onEvent(FinishProfileScreenEvent.NameChanged(it))
        },
        onFocusRelease = {
            onEvent(FinishProfileScreenEvent.ValidateName)
        }
    )
    SurnameInput(
        value = state.surname,
        error = state.surnameError?.asString(),
        focusManager = focusManager,
        onValueChange = {
            onEvent(FinishProfileScreenEvent.SurnameChanged(it))
        },
        onFocusRelease = {
            onEvent(FinishProfileScreenEvent.ValidateSurname)
        }
    )
    EmailInput(
        value = state.email,
        error = state.emailError?.asString(),
        focusManager = focusManager,
        onValueChange = {
            onEvent(FinishProfileScreenEvent.EmailChanged(it))
        },
        onFocusRelease = {
            onEvent(FinishProfileScreenEvent.ValidateEmail)
        }
    )
    PhoneNumberInput(
        title = stringResource(id = R.string.profile_phone_number),
        value = state.phoneNumber,
        isEnabled = false
    )
    BirthDateInput(
        value = state.formattedBirthDate,
        error = state.birthDateError?.asString(),
        datePickerValue = state.birthDate,
        focusManager = focusManager,
        onValueChange = {
            onEvent(FinishProfileScreenEvent.BirthDateChanged(it))
            onEvent(FinishProfileScreenEvent.ValidateBirthDate)
        },
    )
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun FinishProfileInputFormPreview() {
    AnimealTheme {
        Surface {
            Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
                FinishProfileInputForm(
                    state = FinishProfileState(),
                    focusManager = LocalFocusManager.current,
                    onEvent = {}
                )
            }
        }
    }
}