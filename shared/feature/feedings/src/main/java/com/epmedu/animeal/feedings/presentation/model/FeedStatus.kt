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
    PENDING_ORANGE(R.string.feed_status_pending, R.drawable.ic_pending_orange, CustomColor.StatusYellow),
    PENDING_RED(R.string.feed_status_pending, R.drawable.ic_pending_red, CustomColor.StatusRed),
    PENDING_GREY(R.string.feed_status_pending, R.drawable.ic_pending_grey, CustomColor.StatusGrey),
    REJECTED(R.string.feed_status_rejected, R.drawable.ic_rejected, CustomColor.StatusRed),
    OUTDATED(R.string.feed_status_outdated, R.drawable.ic_outdated, CustomColor.StatusRed),
    IN_PROGRESS(R.string.feeding_in_progress, R.drawable.ic_pending_grey, CustomColor.StatusGrey),
}

fun FeedingStatus.toFeedStatus(deltaTime: Long): FeedStatus =
    when (this) {
        FeedingStatus.approved -> FeedStatus.APPROVED
        FeedingStatus.pending -> when (deltaTime) {
            in 0..ORANGE_TIME -> FeedStatus.PENDING_GREY
            in ORANGE_TIME..RED_TIME -> FeedStatus.PENDING_ORANGE
            else -> FeedStatus.PENDING_RED
        }
        FeedingStatus.rejected -> FeedStatus.REJECTED
        FeedingStatus.inProgress -> FeedStatus.IN_PROGRESS
        FeedingStatus.outdated -> FeedStatus.OUTDATED
    }

private const val ORANGE_TIME = 1_800_000
private const val RED_TIME = 21_600_000