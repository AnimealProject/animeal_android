package com.epmedu.animeal.foundation.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.epmedu.animeal.resources.R
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate

@Composable
fun DatePickerDialog(
    state: MaterialDialogState,
    initialDate: LocalDate,
    onPositiveClick: () -> Unit,
    onNegativeClick: () -> Unit,
    onCloseRequest: () -> Unit,
    onDatePicked: (LocalDate) -> Unit,
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
        datepicker(initialDate = initialDate) {
            onDatePicked(it)
        }
    }
}

@Preview
@Composable
private fun DatePickerDialogPreview() {
    DatePickerDialog(
        state = rememberMaterialDialogState(),
        initialDate = LocalDate.now(),
        onPositiveClick = {},
        onNegativeClick = {},
        onCloseRequest = {},
        onDatePicked = {},
    )
}