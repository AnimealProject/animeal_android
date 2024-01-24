package com.epmedu.animeal.feedings.presentation.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.epmedu.animeal.feeding.domain.model.FeedingStatus
import com.epmedu.animeal.foundation.theme.CustomColor
import com.epmedu.animeal.resources.R
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

enum class FeedingModelStatus(
    @StringRes val titleId: Int,
    @DrawableRes val iconId: Int,
    val color: Color
) {
    APPROVED(R.string.feed_status_approved, R.drawable.ic_approved, CustomColor.StatusGreen),
    PENDING_ORANGE(R.string.feed_status_pending, R.drawable.ic_pending_orange, CustomColor.StatusYellow),
    PENDING_RED(R.string.feed_status_pending, R.drawable.ic_pending_red, CustomColor.StatusRed),
    PENDING_GREY(R.string.feed_status_pending, R.drawable.ic_pending_grey, CustomColor.StatusGrey),
    REJECTED(R.string.feed_status_rejected, R.drawable.ic_rejected, CustomColor.StatusRed),
    OUTDATED(R.string.feed_status_outdated, R.drawable.ic_outdated, CustomColor.StatusMaroon),
}

fun FeedingStatus.toFeedingModelStatus(deltaTime: Long): FeedingModelStatus? =
    when (this) {
        FeedingStatus.Approved -> FeedingModelStatus.APPROVED
        FeedingStatus.Pending -> when (deltaTime) {
            in 0..orangeTime -> FeedingModelStatus.PENDING_GREY
            in orangeTime..redTime -> FeedingModelStatus.PENDING_ORANGE
            else -> FeedingModelStatus.PENDING_RED
        }
        FeedingStatus.Rejected -> FeedingModelStatus.REJECTED
        FeedingStatus.Outdated -> FeedingModelStatus.OUTDATED
        else -> null
    }

private val orangeTime = 30.minutes.inWholeMilliseconds
private val redTime = 1.hours.inWholeMilliseconds