package com.epmedu.animeal.feedings.presentation.model

import com.epmedu.animeal.foundation.common.UiText
import kotlinx.collections.immutable.ImmutableList

sealed class RejectionReasonRequestType {

    data class ReasonSelection(
        val reasons: ImmutableList<RejectionReasonType>,
        val selectedReason: RejectionReasonType = RejectionReasonType.NoFood
    ) : RejectionReasonRequestType()

    data class Comment(
        val comment: String = "",
        val error: UiText = UiText.Empty
    ) : RejectionReasonRequestType()
}