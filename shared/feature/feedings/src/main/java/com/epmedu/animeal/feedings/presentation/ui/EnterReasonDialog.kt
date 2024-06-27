package com.epmedu.animeal.feedings.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.epmedu.animeal.feedings.presentation.model.RejectionReasonRequestType
import com.epmedu.animeal.foundation.dialog.AnimealQuestionDialog
import com.epmedu.animeal.foundation.input.TextInputField
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.resources.R

private const val MAX_COMMENT_LENGTH = 255

@Composable
internal fun EnterReasonDialog(
    state: RejectionReasonRequestType.Comment,
    onReasonChange: (String) -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AnimealQuestionDialog(
        title = stringResource(id = R.string.rejection_reason_comment_dialog_title),
        subtitle = stringResource(id = R.string.rejection_reason_comment_dialog_subtitle),
        titleFontSize = 24.sp,
        dismissText = stringResource(id = R.string.go_back),
        acceptText = stringResource(id = R.string.reject),
        onDismiss = onDismiss,
        onConfirm = onConfirm
    ) {
        TextInputField(
            title = null,
            hint = stringResource(id = R.string.type_hint),
            onValueChange = { newValue ->
                if (newValue.length <= MAX_COMMENT_LENGTH) {
                    onReasonChange(newValue)
                }
            },
            value = state.comment,
            errorText = state.error.asString()
        )
    }
}

@AnimealPreview
@Composable
private fun RejectionReasonDialogPreview() {
    AnimealTheme {
        EnterReasonDialog(
            state = RejectionReasonRequestType.Comment(),
            onReasonChange = {},
            onConfirm = {},
            onDismiss = {}
        )
    }
}
