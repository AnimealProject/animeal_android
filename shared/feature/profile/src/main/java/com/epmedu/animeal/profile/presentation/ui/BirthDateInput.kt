package com.epmedu.animeal.profile.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalFocusManager
import com.epmedu.animeal.extensions.tryParseDate
import com.epmedu.animeal.foundation.dialog.DatePickerDialog
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate

@Composable
internal fun BirthDateInput(
    value: String,
    onValueChange: (LocalDate) -> Unit,
    error: String,
    clickable: Boolean = true,
) {
    val focusManager = LocalFocusManager.current
    val dialogState = rememberMaterialDialogState()

    BirthDateInputContent(
        value = value,
        clickable = clickable,
        error = error,
        onClick = {
            focusManager.clearFocus()
            dialogState.show()
        }
    )
    DatePickerDialog(
        state = dialogState,
        date = tryParseDate(value) ?: LocalDate.now(),
        onPositiveClick = { dialogState.hide(focusManager) },
        onNegativeClick = { dialogState.hide(focusManager) },
        onCloseRequest = { dialogState.hide(focusManager) },
        onPickDate = {
            onValueChange(it)
            dialogState.hide(focusManager)
        }
    )
}