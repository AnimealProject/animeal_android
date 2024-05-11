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
    AUTO_APPROVED(
        R.string.feed_status_auto_approved,
        R.drawable.ic_approved,
        CustomColor.StatusGreen
    ),
    APPROVED(R.string.feed_status_approved, R.drawable.ic_approved, CustomColor.StatusGreen),
    PENDING_ORANGE(
        R.string.feed_status_pending,
        R.drawable.ic_pending_orange,
        CustomColor.StatusYellow
    ),
    PENDING_RED(R.string.feed_status_pending, R.drawable.ic_pending_red, CustomColor.StatusRed),
    PENDING_GREY(R.string.feed_status_pending, R.drawable.ic_pending_grey, CustomColor.StatusGrey),
    REJECTED(R.string.feed_status_rejected, R.drawable.ic_rejected, CustomColor.StatusRed),
    OUTDATED(R.string.feed_status_outdated, R.drawable.ic_outdated, CustomColor.StatusMaroon),
}

fun FeedingModelStatus.isPending() = this == FeedingModelStatus.PENDING_RED ||
    this == FeedingModelStatus.PENDING_ORANGE ||
    this == FeedingModelStatus.PENDING_GREY

fun FeedingStatus.toFeedingModelStatus(
    deltaTime: Long,
    isFeederTrusted: Boolean
): FeedingModelStatus? =
    when (this) {
        FeedingStatus.Approved -> if (isFeederTrusted) FeedingModelStatus.AUTO_APPROVED else FeedingModelStatus.APPROVED
        FeedingStatus.Pending -> when (deltaTime) {
            in greyTimeRange -> FeedingModelStatus.PENDING_GREY
            in orangeTimeRange -> FeedingModelStatus.PENDING_ORANGE
            in redTimeRange -> FeedingModelStatus.PENDING_RED
            else -> FeedingModelStatus.OUTDATED
        }

        FeedingStatus.Rejected -> FeedingModelStatus.REJECTED
        FeedingStatus.Outdated -> FeedingModelStatus.OUTDATED
        else -> null
    }

private val greyTimeRange = 0..30.minutes.inWholeMilliseconds
private val orangeTimeRange = 30.minutes.inWholeMilliseconds..1.hours.inWholeMilliseconds
private val redTimeRange = 1.hours.inWholeMilliseconds..12.hours.inWholeMilliseconds