package com.epmedu.animeal.feedings.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epmedu.animeal.feedings.presentation.model.RejectionReasonRequestType
import com.epmedu.animeal.feedings.presentation.model.RejectionReasonType
import com.epmedu.animeal.foundation.dialog.AnimealQuestionDialog
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.radiobutton.AnimealRadioButton
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.resources.R
import kotlinx.collections.immutable.toPersistentList

@Composable
internal fun RejectionReasonDialog(
    state: RejectionReasonRequestType.ReasonSelection,
    onConfirm: (RejectionReasonType) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedReason by remember { mutableStateOf(state.selectedReason) }

    AnimealQuestionDialog(
        title = stringResource(id = R.string.rejection_reason_selection_dialog_title),
        subtitle = stringResource(id = R.string.rejection_reason_selection_dialog_subtitle),
        titleFontSize = 24.sp,
        dismissText = stringResource(id = R.string.go_back),
        acceptText = stringResource(id = R.string.reject),
        onDismiss = onDismiss,
        onConfirm = { onConfirm(selectedReason) }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            state.reasons.forEach { reasonType ->
                AnimealRadioButton(
                    modifier = Modifier.padding(vertical = 18.dp),
                    selected = reasonType == selectedReason,
                    onClick = { selectedReason = reasonType },
                    label = stringResource(id = reasonType.text)
                )
            }
        }
    }
}

@AnimealPreview
@Composable
private fun RejectionReasonDialogPreview() {
    AnimealTheme {
        RejectionReasonDialog(
            state = RejectionReasonRequestType.ReasonSelection(
                reasons = RejectionReasonType.entries.toPersistentList()
            ),
            onConfirm = {},
            onDismiss = {}
        )
    }
}
