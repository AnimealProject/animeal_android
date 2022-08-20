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
import com.epmedu.animeal.signup.finishprofile.presentation.FinishProfileScreenEvent.BirthDateChanged
import com.epmedu.animeal.signup.finishprofile.presentation.FinishProfileScreenEvent.EmailChanged
import com.epmedu.animeal.signup.finishprofile.presentation.FinishProfileScreenEvent.NameChanged
import com.epmedu.animeal.signup.finishprofile.presentation.FinishProfileScreenEvent.SurnameChanged
import com.epmedu.animeal.signup.finishprofile.presentation.FinishProfileScreenEvent.ValidateBirthDate
import com.epmedu.animeal.signup.finishprofile.presentation.FinishProfileScreenEvent.ValidateEmail
import com.epmedu.animeal.signup.finishprofile.presentation.FinishProfileScreenEvent.ValidateName
import com.epmedu.animeal.signup.finishprofile.presentation.FinishProfileScreenEvent.ValidateSurname
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
            onEvent(NameChanged(it))
        },
        onFocusRelease = {
            onEvent(ValidateName)
        }
    )
    SurnameInput(
        value = state.surname,
        error = state.surnameError?.asString(),
        focusManager = focusManager,
        onValueChange = {
            onEvent(SurnameChanged(it))
        },
        onFocusRelease = {
            onEvent(ValidateSurname)
        }
    )
    EmailInput(
        value = state.email,
        error = state.emailError?.asString(),
        focusManager = focusManager,
        onValueChange = {
            onEvent(EmailChanged(it))
        },
        onFocusRelease = {
            onEvent(ValidateEmail)
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
            onEvent(BirthDateChanged(it))
            onEvent(ValidateBirthDate)
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