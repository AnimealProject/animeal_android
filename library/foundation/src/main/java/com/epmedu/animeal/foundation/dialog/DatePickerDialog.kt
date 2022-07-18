package com.epmedu.animeal.foundation.dialog

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.epmedu.animeal.resources.R
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate

@Composable
fun DatePickerDialog(
    initialDate: LocalDate,
    shouldShowDialog: MutableState<Boolean>,
    onDatePicked: (LocalDate) -> Unit
) {
    val dialogState = rememberMaterialDialogState()
    MaterialDialog(
        dialogState = dialogState,
        buttons = {
            positiveButton(stringResource(id = R.string.ok), onClick = { shouldShowDialog.value = false })
            negativeButton(stringResource(id = R.string.cancel), onClick = { shouldShowDialog.value = false })
        },
        onCloseRequest = { shouldShowDialog.value = false }
    ) {
        datepicker(initialDate = initialDate) {
            onDatePicked(it)
            shouldShowDialog.value = false
        }
    }
    if (shouldShowDialog.value) {
        dialogState.show()
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
private fun DatePickerDialogPreview() {
    DatePickerDialog(shouldShowDialog = mutableStateOf(true), initialDate = LocalDate.now()) {}
}