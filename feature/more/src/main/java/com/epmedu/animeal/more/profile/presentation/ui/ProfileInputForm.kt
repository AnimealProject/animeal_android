package com.epmedu.animeal.more.profile.presentation.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.input.PhoneNumberInput
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.more.profile.presentation.ProfileScreenEvent
import com.epmedu.animeal.more.profile.presentation.ProfileScreenEvent.BirthDateChanged
import com.epmedu.animeal.more.profile.presentation.ProfileScreenEvent.EmailChanged
import com.epmedu.animeal.more.profile.presentation.ProfileScreenEvent.NameChanged
import com.epmedu.animeal.more.profile.presentation.ProfileScreenEvent.SurnameChanged
import com.epmedu.animeal.more.profile.presentation.viewmodel.ProfileState
import com.epmedu.animeal.more.profile.presentation.viewmodel.ProfileState.FormState.READ_ONLY
import com.epmedu.animeal.signup.finishprofile.presentation.ui.BirthDateInput
import com.epmedu.animeal.signup.finishprofile.presentation.ui.EmailInput
import com.epmedu.animeal.signup.finishprofile.presentation.ui.NameInput
import com.epmedu.animeal.signup.finishprofile.presentation.ui.SurnameInput

@Composable
internal fun ProfileInputForm(
    state: ProfileState,
    onEvent: (ProfileScreenEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    val isFormEnabled = state.formState != READ_ONLY

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        NameInput(
            value = state.profile.name,
            onValueChange = { onEvent(NameChanged(it)) },
            error = state.nameError.asString(),
            isEnabled = isFormEnabled,
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        )
        SurnameInput(
            value = state.profile.surname,
            onValueChange = { onEvent(SurnameChanged(it)) },
            error = state.surnameError.asString(),
            isEnabled = isFormEnabled,
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        )
        EmailInput(
            value = state.profile.email,
            onValueChange = { onEvent(EmailChanged(it)) },
            error = state.emailError.asString(),
            isEnabled = isFormEnabled,
            onDone = { focusManager.clearFocus() }
        )
        PhoneNumberInput(
            value = state.phoneNumber,
            isEnabled = false
        )
        BirthDateInput(
            value = state.profile.birthDate,
            onValueChange = { onEvent(BirthDateChanged(it)) },
            error = state.birthDateError.asString(),
            clickable = isFormEnabled,
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ProfileInputFormPreview() {
    AnimealTheme {
        ProfileInputForm(
            state = ProfileState(),
            onEvent = {}
        )
    }
}