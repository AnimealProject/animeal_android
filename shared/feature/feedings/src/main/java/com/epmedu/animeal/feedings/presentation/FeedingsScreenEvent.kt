package com.epmedu.animeal.feedings.presentation

import com.epmedu.animeal.feedings.presentation.model.FeedingModel
import com.epmedu.animeal.feedings.presentation.model.RejectionReasonType
import com.epmedu.animeal.feedings.presentation.viewmodel.FeedingFilterCategory

sealed interface FeedingsScreenEvent {
    data class UpdateCurrentFeeding(val feeding: FeedingModel?) : FeedingsScreenEvent
    data class UpdateCategoryEvent(val category: FeedingFilterCategory) : FeedingsScreenEvent
    data class ApproveClicked(val feeding: FeedingModel) : FeedingsScreenEvent
    data class RejectClicked(val feeding: FeedingModel) : FeedingsScreenEvent

    data class RejectionReasonSelected(
        val reasonType: RejectionReasonType,
        val reasonText: String
    ) : FeedingsScreenEvent

    object RejectionReasonSelectionDismissed : FeedingsScreenEvent
    data class RejectionCommentUpdated(val comment: String) : FeedingsScreenEvent
    data class RejectionCommentConfirmed(val comment: String) : FeedingsScreenEvent
    object RejectionCommentDismissed : FeedingsScreenEvent
    object ErrorShown : FeedingsScreenEvent
}