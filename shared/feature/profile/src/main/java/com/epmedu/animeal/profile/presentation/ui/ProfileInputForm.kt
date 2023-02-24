package com.epmedu.animeal.profile.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.input.Flag
import com.epmedu.animeal.foundation.input.PhoneNumberInput
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.profile.domain.model.Region
import com.epmedu.animeal.profile.domain.model.flagEmoji
import com.epmedu.animeal.profile.presentation.ProfileInputFormEvent
import com.epmedu.animeal.profile.presentation.ProfileInputFormEvent.BirthDateChanged
import com.epmedu.animeal.profile.presentation.ProfileInputFormEvent.EmailChanged
import com.epmedu.animeal.profile.presentation.ProfileInputFormEvent.NameChanged
import com.epmedu.animeal.profile.presentation.ProfileInputFormEvent.PhoneNumberChanged
import com.epmedu.animeal.profile.presentation.ProfileInputFormEvent.SurnameChanged
import com.epmedu.animeal.profile.presentation.viewmodel.ProfileState
import com.epmedu.animeal.profile.presentation.viewmodel.ProfileState.FormState.READ_ONLY
import com.epmedu.animeal.resources.R

@Composable
fun ProfileInputForm(
    state: ProfileState,
    onEvent: (ProfileInputFormEvent) -> Unit,
    modifier: Modifier = Modifier,
    onCountryClick: (() -> Unit)? = null,
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
            PhoneInput(onEvent, onCountryClick, focusManager)
            BirthDateInput(
                value = profile.birthDate,
                onValueChange = { onEvent(BirthDateChanged(it)) },
                error = birthDateError.asString(),
                clickable = isFormEnabled,
            )
        }
    }
}

@Composable
private fun ProfileState.PhoneInput(
    onEvent: (ProfileInputFormEvent) -> Unit,
    onCountryClick: (() -> Unit)? = null,
    focusManager: FocusManager
) {
    PhoneNumberInput(
        value = phoneNumber,
        prefix = prefix,
        flag = when (region) {
            Region.GE -> Flag(R.drawable.ic_georgia)
            else -> Flag(emojiFlag = profile.phoneNumberRegion.flagEmoji())
        },
        format = format,
        numberLength = numberLength,
        useNumberFormatter = region == Region.GE,
        onValueChange = { onEvent(PhoneNumberChanged(it)) },
        error = phoneNumberError.asString(),
        isEnabled = isPhoneNumberEnabled,
        isFlagClickable = isPhoneNumberEnabled,
        onCountryClick = onCountryClick,
        onDone = { focusManager.clearFocus() },
    )
}

@AnimealPreview
@Composable
private fun ProfileInputFormPreview() {
    com.epmedu.animeal.foundation.theme.AnimealTheme {
        ProfileInputForm(
            state = ProfileState(),
            {}
        )
    }
}