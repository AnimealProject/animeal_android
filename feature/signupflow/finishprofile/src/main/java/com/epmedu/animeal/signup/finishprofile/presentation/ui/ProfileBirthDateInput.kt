package com.epmedu.animeal.signup.finishprofile.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import com.epmedu.animeal.extensions.tryParseDate
import com.epmedu.animeal.foundation.dialog.DatePickerDialog
import com.epmedu.animeal.foundation.input.BirthDateInput
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.resources.R
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate

// TODO: Move to a separate module
@Suppress("LongParameterList")
@Composable
fun BirthDateInput(
    value: String,
    onValueChange: (LocalDate) -> Unit,
    error: String,
    clickable: Boolean = true,
) {
    val focusManager = LocalFocusManager.current
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
        onValueChange = { focusManager.clearFocus() },
    )
    DatePickerDialog(
        state = dialogState,
        date = tryParseDate(value) ?: LocalDate.now(),
        onPositiveClick = { showDialog = false },
        onNegativeClick = { showDialog = false },
        onCloseRequest = { showDialog = false },
        onPickDate = {
            onValueChange(it)
            showDialog = false
        }
    )
}

@AnimealPreview
@Composable
private fun BirthDateInputPreview() {
    AnimealTheme {
        BirthDateInput(
            value = "1, Sep 1939",
            onValueChange = {},
            error = ""
        )
    }
}