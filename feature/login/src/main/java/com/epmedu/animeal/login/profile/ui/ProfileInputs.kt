package com.epmedu.animeal.login.profile.ui

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.epmedu.animeal.extensions.DAY_MONTH_YEAR_DOT_FORMATTER
import com.epmedu.animeal.extensions.formatDateToString
import com.epmedu.animeal.foundation.dialog.DatePickerDialog
import com.epmedu.animeal.foundation.input.TextInputField
import com.epmedu.animeal.login.profile.presentation.FinishProfileEvent
import com.epmedu.animeal.resources.R
import java.time.LocalDate

@Composable
fun NameInput(
    value: String,
    error: String?,
    focusManager: FocusManager,
    isEnabled: Boolean = true,
    onValueChange: (String) -> Unit,
    onFocusRelease: () -> Unit,
) {
    TextInputField(
        isEnabled = isEnabled,
        title = stringResource(id = R.string.profile_name_title),
        hint = stringResource(id = R.string.profile_name_hint),
        onValueChange = onValueChange,
        value = value,
        errorText = error,
        onClearFocus = onFocusRelease,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
    )
}

@Composable
fun SurnameInput(
    value: String,
    error: String?,
    focusManager: FocusManager,
    isEnabled: Boolean = true,
    onValueChange: (String) -> Unit,
    onFocusRelease: () -> Unit,
) {
    TextInputField(
        isEnabled = isEnabled,
        title = stringResource(id = R.string.profile_surname_title),
        hint = stringResource(id = R.string.profile_surname_hint),
        onValueChange = onValueChange,
        value = value,
        errorText = error,
        onClearFocus = onFocusRelease,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
    )
}

@Composable
fun EmailInput(
    value: String,
    error: String?,
    focusManager: FocusManager,
    isEnabled: Boolean = true,
    onValueChange: (String) -> Unit,
    onFocusRelease: () -> Unit,
) {
    TextInputField(
        isEnabled = isEnabled,
        title = stringResource(id = R.string.profile_email_title),
        hint = stringResource(id = R.string.profile_email_hint),
        onValueChange = onValueChange,
        value = value,
        errorText = error,
        onClearFocus = onFocusRelease,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Email
        ),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
    )
}

@Suppress("LongParameterList")
@Composable
fun BirthDateInput(
    value: String,
    error: String?,
    initialDate: LocalDate,
    focusManager: FocusManager,
    isEnabled: Boolean = true,
    onValueChange: (String) -> Unit,
    onFocusRelease: () -> Unit,
) {
    val shouldOpenDialog = remember { mutableStateOf(false) }
    com.epmedu.animeal.foundation.input.BirthDateInput(
        title = stringResource(id = R.string.profile_birth_date),
        isEnabled = isEnabled,
        onIconClick = {
            focusManager.clearFocus()
            shouldOpenDialog.value = true
        },
        onValueChange = onValueChange,
        value = value,
        errorText = error,
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
        initialDate = initialDate,
        shouldShowDialog = shouldOpenDialog
    ) {
        val formattedDate = formatDateToString(it, DAY_MONTH_YEAR_DOT_FORMATTER)
        onValueChange(formattedDate.replace(".", ""))
        onFocusRelease()
    }
}