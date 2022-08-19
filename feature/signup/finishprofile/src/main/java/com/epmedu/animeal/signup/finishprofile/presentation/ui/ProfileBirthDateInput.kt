package com.epmedu.animeal.signup.finishprofile.presentation.ui

import android.content.res.Configuration
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.epmedu.animeal.foundation.dialog.DatePickerDialog
import com.epmedu.animeal.foundation.input.BirthDateInput
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.resources.R
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate

// TODO: Move to a separate module
@Suppress("LongParameterList")
@Composable
fun BirthDateInput(
    value: String,
    error: String?,
    clickable: Boolean = true,
    datePickerValue: LocalDate,
    focusManager: FocusManager,
    onValueChange: (LocalDate) -> Unit,
) {
    val dialogState = rememberMaterialDialogState()
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        dialogState.show()
    }

    BirthDateInput(
        title = stringResource(R.string.profile_birth_date),
        value = value,
        errorText = error,
        clickable = clickable,
        onClick = {
            focusManager.clearFocus()
            showDialog = true
        },
        onValueChange = {
            focusManager.clearFocus()
        },
    )
    DatePickerDialog(
        state = dialogState,
        date = datePickerValue,
        onPositiveClick = { showDialog = false },
        onNegativeClick = { showDialog = false },
        onCloseRequest = { showDialog = false },
        onDatePicked = {
            onValueChange(it)
            showDialog = false
        }
    )
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun BirthDateInputPreview() {
    AnimealTheme {
        Surface {
            BirthDateInput(
                value = "1, Sep 1939",
                error = null,
                datePickerValue = LocalDate.now(),
                focusManager = LocalFocusManager.current,
                onValueChange = {},
            )
        }
    }
}