package com.epmedu.animeal.login.profile.ui

import android.content.res.Configuration
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.epmedu.animeal.extensions.DAY_MONTH_YEAR_DOT_FORMATTER
import com.epmedu.animeal.extensions.formatDateToString
import com.epmedu.animeal.foundation.dialog.DatePickerDialog
import com.epmedu.animeal.foundation.input.BirthDateInput
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.login.profile.presentation.FinishProfileEvent
import com.epmedu.animeal.resources.R
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate

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
    val dialogState = rememberMaterialDialogState()
    val showDialog = remember { mutableStateOf(false) }

    if (showDialog.value) {
        dialogState.show()
    }

    BirthDateInput(
        title = stringResource(id = R.string.profile_birth_date),
        isEnabled = isEnabled,
        onIconClick = {
            focusManager.clearFocus()
            showDialog.value = true
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
        state = dialogState,
        initialDate = initialDate,
        onPositiveClick = { showDialog.value = false },
        onNegativeClick = { showDialog.value = false },
        onCloseRequest = { showDialog.value = false },
        onDatePicked = {
            val formattedDate = formatDateToString(it, DAY_MONTH_YEAR_DOT_FORMATTER)
            onValueChange(formattedDate.replace(".", ""))
            onFocusRelease()
            showDialog.value = false
        }
    )
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun BirthDateInputPreview() {
    AnimealTheme {
        BirthDateInput(
            value = "11.11.1900",
            error = null,
            initialDate = LocalDate.now(),
            focusManager = LocalFocusManager.current,
            onValueChange = {},
            onFocusRelease = {}
        )
    }
}