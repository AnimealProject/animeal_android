package com.epmedu.animeal.feedings.presentation.model

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.epmedu.animeal.feeding.domain.model.FeedingStatus
import com.epmedu.animeal.foundation.icons.AnimealIcons
import com.epmedu.animeal.foundation.icons.colored.Approved
import com.epmedu.animeal.foundation.icons.colored.Outdated
import com.epmedu.animeal.foundation.icons.colored.PendingGrey
import com.epmedu.animeal.foundation.icons.colored.PendingOrange
import com.epmedu.animeal.foundation.icons.colored.PendingRed
import com.epmedu.animeal.foundation.icons.colored.Rejected
import com.epmedu.animeal.foundation.theme.CustomColor
import com.epmedu.animeal.resources.R
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

enum class FeedingModelStatus(
    @StringRes val titleId: Int,
    val icon: ImageVector,
    val color: Color
) {
    AUTO_APPROVED(
        titleId = R.string.feed_status_auto_approved,
        icon = AnimealIcons.Colored.Approved,
        color = CustomColor.StatusGreen
    ),
    APPROVED(
        titleId = R.string.feed_status_approved,
        icon = AnimealIcons.Colored.Approved,
        color = CustomColor.StatusGreen
    ),
    PENDING_ORANGE(
        titleId = R.string.feed_status_pending,
        icon = AnimealIcons.Colored.PendingOrange,
        color = CustomColor.StatusYellow
    ),
    PENDING_RED(
        titleId = R.string.feed_status_pending,
        icon = AnimealIcons.Colored.PendingRed,
        color = CustomColor.StatusRed
    ),
    PENDING_GREY(
        titleId = R.string.feed_status_pending,
        icon = AnimealIcons.Colored.PendingGrey,
        color = CustomColor.StatusGrey
    ),
    REJECTED(
        titleId = R.string.feed_status_rejected,
        icon = AnimealIcons.Colored.Rejected,
        color = CustomColor.StatusRed
    ),
    OUTDATED(
        titleId = R.string.feed_status_outdated,
        icon = AnimealIcons.Colored.Outdated,
        color = CustomColor.StatusMaroon
    ),
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