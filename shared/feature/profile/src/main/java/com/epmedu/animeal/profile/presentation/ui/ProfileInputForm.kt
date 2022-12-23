package com.epmedu.animeal.profile.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.input.PhoneNumberInput
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.profile.presentation.ProfileInputFormEvent
import com.epmedu.animeal.profile.presentation.ProfileInputFormEvent.BirthDateChanged
import com.epmedu.animeal.profile.presentation.ProfileInputFormEvent.EmailChanged
import com.epmedu.animeal.profile.presentation.ProfileInputFormEvent.NameChanged
import com.epmedu.animeal.profile.presentation.ProfileInputFormEvent.PhoneNumberChanged
import com.epmedu.animeal.profile.presentation.ProfileInputFormEvent.SurnameChanged
import com.epmedu.animeal.profile.presentation.viewmodel.ProfileState
import com.epmedu.animeal.profile.presentation.viewmodel.ProfileState.FormState.READ_ONLY

@Composable
fun ProfileInputForm(
    state: ProfileState,
    onEvent: (ProfileInputFormEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    val isFormEnabled = state.formState != READ_ONLY

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        with(state) {
            NameInput(
                value = profile.name,
                onValueChange = { onEvent(NameChanged(it)) },
                error = nameError.asString(),
                isEnabled = isFormEnabled,
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            )
            SurnameInput(
                value = profile.surname,
                onValueChange = { onEvent(SurnameChanged(it)) },
                error = surnameError.asString(),
                isEnabled = isFormEnabled,
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            )
            EmailInput(
                value = profile.email,
                onValueChange = { onEvent(EmailChanged(it)) },
                error = emailError.asString(),
                imeAction = if (isPhoneNumberEnabled) ImeAction.Next else ImeAction.Done,
                isEnabled = isFormEnabled,
                onNext = { focusManager.moveFocus(FocusDirection.Down) },
                onDone = { focusManager.clearFocus() }
            )
            PhoneNumberInput(
                value = phoneNumber,
                prefix = profile.phoneNumberPrefix,
                onValueChange = { onEvent(PhoneNumberChanged(it)) },
                error = phoneNumberError.asString(),
                isEnabled = isPhoneNumberEnabled,
                onDone = { focusManager.clearFocus() },
            )
            BirthDateInput(
                value = profile.birthDate,
                onValueChange = { onEvent(BirthDateChanged(it)) },
                error = birthDateError.asString(),
                clickable = isFormEnabled,
            )
        }
    }
}

@AnimealPreview
@Composable
private fun ProfileInputFormPreview() {
    com.epmedu.animeal.foundation.theme.AnimealTheme {
        ProfileInputForm(
            state = ProfileState(),
            onEvent = {}
        )
    }
}