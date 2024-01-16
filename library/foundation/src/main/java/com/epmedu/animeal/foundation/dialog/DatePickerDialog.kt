package com.epmedu.animeal.foundation.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.resources.R
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate

@Composable
fun DatePickerDialog(
    state: MaterialDialogState,
    date: LocalDate,
    onPositiveClick: () -> Unit,
    onNegativeClick: () -> Unit,
    onCloseRequest: () -> Unit,
    onPickDate: (LocalDate) -> Unit,
) {
    MaterialDialog(
        dialogState = state,
        buttons = {
            positiveButton(
                text = stringResource(id = R.string.ok),
                onClick = onPositiveClick
            )
            negativeButton(
                text = stringResource(id = R.string.cancel),
                onClick = onNegativeClick
            )
        },
        onCloseRequest = {
            onCloseRequest()
        }
    ) {
        datepicker(
            initialDate = date,
            allowedDateValidator = {
                it.isBefore(LocalDate.now().plusDays(1))
                it.isAfter(LocalDate.now().minusYears(150))
            }
        ) {
            onPickDate(it)
        }
    }
}

@AnimealPreview
@Composable
private fun DatePickerDialogPreview() {
    DatePickerDialog(
        state = rememberMaterialDialogState(),
        date = LocalDate.now(),
        onPositiveClick = {},
        onNegativeClick = {},
        onCloseRequest = {},
        onPickDate = {},
    )
}