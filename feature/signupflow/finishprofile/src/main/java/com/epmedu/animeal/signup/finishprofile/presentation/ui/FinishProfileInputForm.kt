package com.epmedu.animeal.signup.finishprofile.presentation.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.input.PhoneNumberInput
import com.epmedu.animeal.foundation.theme.AnimealTheme
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
        onValueChange = { onEvent(NameChanged(it)) },
        error = state.nameError?.asString().orEmpty(),
        onClearFocus = { onEvent(ValidateName) },
        onNext = { focusManager.moveFocus(FocusDirection.Down) }
    )
    SurnameInput(
        value = state.surname,
        onValueChange = { onEvent(SurnameChanged(it)) },
        error = state.surnameError?.asString().orEmpty(),
        onClearFocus = { onEvent(ValidateSurname) },
        onNext = { focusManager.moveFocus(FocusDirection.Down) }
    )
    EmailInput(
        value = state.email,
        onValueChange = { onEvent(EmailChanged(it)) },
        error = state.emailError?.asString().orEmpty(),
        onClearFocus = { onEvent(ValidateEmail) },
        onDone = { focusManager.clearFocus() }
    )
    PhoneNumberInput(
        value = state.phoneNumber,
        isEnabled = false
    )
    BirthDateInput(
        value = state.formattedBirthDate,
        onValueChange = {
            onEvent(BirthDateChanged(it))
            onEvent(ValidateBirthDate)
        },
        error = state.birthDateError?.asString().orEmpty()
    )
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun FinishProfileInputFormPreview() {
    AnimealTheme {
        Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
            FinishProfileInputForm(
                state = FinishProfileState(),
                focusManager = LocalFocusManager.current,
                onEvent = {}
            )
        }
    }
}