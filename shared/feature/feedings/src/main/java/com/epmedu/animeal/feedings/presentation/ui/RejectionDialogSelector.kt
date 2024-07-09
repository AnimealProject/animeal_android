package com.epmedu.animeal.feedings.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.epmedu.animeal.feedings.presentation.FeedingsScreenEvent
import com.epmedu.animeal.feedings.presentation.FeedingsScreenEvent.RejectionCommentConfirmed
import com.epmedu.animeal.feedings.presentation.FeedingsScreenEvent.RejectionCommentDismissed
import com.epmedu.animeal.feedings.presentation.FeedingsScreenEvent.RejectionCommentUpdated
import com.epmedu.animeal.feedings.presentation.FeedingsScreenEvent.RejectionReasonSelected
import com.epmedu.animeal.feedings.presentation.FeedingsScreenEvent.RejectionReasonSelectionDismissed
import com.epmedu.animeal.feedings.presentation.model.RejectionReasonRequestType
import com.epmedu.animeal.feedings.presentation.viewmodel.FeedingsState

@Composable
fun RejectionDialogSelector(
    state: FeedingsState,
    onEvent: (FeedingsScreenEvent) -> Unit
) {
    val context = LocalContext.current

    state.rejectionReasonRequestType?.let { rejectionReasonState ->
        when (rejectionReasonState) {
            is RejectionReasonRequestType.ReasonSelection -> {
                RejectionReasonDialog(
                    rejectionReasonState,
                    onConfirm = { reasonType ->
                        val reasonText = context.getString(reasonType.text)
                        onEvent(RejectionReasonSelected(reasonType, reasonText))
                    },
                    onDismiss = { onEvent(RejectionReasonSelectionDismissed) }
                )
            }
            is RejectionReasonRequestType.Comment -> {
                EnterReasonDialog(
                    rejectionReasonState,
                    onReasonChange = { onEvent(RejectionCommentUpdated(it)) },
                    onConfirm = { onEvent(RejectionCommentConfirmed(rejectionReasonState.comment)) },
                    onDismiss = { onEvent(RejectionCommentDismissed) }
                )
            }
        }
    }
}