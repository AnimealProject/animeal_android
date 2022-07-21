package com.epmedu.animeal.foundation.dialog

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    shouldShowDialog: Boolean,
    onDatePicked: (LocalDate) -> Unit
) {
    val dialogState = rememberMaterialDialogState()
    var showDialog  by remember { mutableStateOf(shouldShowDialog) }
    MaterialDialog(
        dialogState = dialogState,
        buttons = {
            positiveButton(stringResource(id = R.string.ok), onClick = { showDialog = false })
            negativeButton(stringResource(id = R.string.cancel), onClick = { showDialog = false })
        },
        onCloseRequest = { showDialog = false }
    ) {
        datepicker(initialDate = initialDate) {
            onDatePicked(it)
            showDialog= false
        }
    }
    if (showDialog) {
        dialogState.show()
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
private fun DatePickerDialogPreview() {
    DatePickerDialog(shouldShowDialog = true, initialDate = LocalDate.now()) {}
}