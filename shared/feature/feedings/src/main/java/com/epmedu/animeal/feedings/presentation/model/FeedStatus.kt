package com.epmedu.animeal.feedings.presentation.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.epmedu.animeal.foundation.theme.CustomColor
import com.epmedu.animeal.resources.R
import type.FeedingStatus

enum class FeedStatus(
    @StringRes val titleId: Int,
    @DrawableRes val iconId: Int,
    val color: Color
) {
    APPROVED(R.string.feed_status_approved, R.drawable.ic_approved, CustomColor.StatusGreen),
    PENDING(R.string.feed_status_pending, R.drawable.ic_pending, CustomColor.StatusYellow),
    REJECTED(R.string.feed_status_rejected, R.drawable.ic_rejected, CustomColor.StatusRed),
    OUTDATED(R.string.feed_status_outdated, R.drawable.ic_outdated, CustomColor.StatusRed),
    IN_PROGRESS(R.string.feed_status_pending, R.drawable.ic_pending, CustomColor.StatusYellow),
}

fun FeedingStatus.toFeedStatus(): FeedStatus =
    when (this) {
        FeedingStatus.approved -> FeedStatus.APPROVED
        FeedingStatus.pending -> FeedStatus.PENDING
        FeedingStatus.rejected -> FeedStatus.REJECTED
        FeedingStatus.inProgress -> FeedStatus.IN_PROGRESS
        FeedingStatus.outdated -> FeedStatus.OUTDATED
    }