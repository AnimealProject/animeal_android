package com.epmedu.animeal.feeding.presentation.model

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.epmedu.animeal.feeding.domain.model.enum.AnimalState
import com.epmedu.animeal.foundation.icons.AnimealIcons
import com.epmedu.animeal.foundation.icons.colored.FaceHappy
import com.epmedu.animeal.foundation.icons.colored.FaceNeutral
import com.epmedu.animeal.foundation.icons.colored.FaceUpset
import com.epmedu.animeal.foundation.icons.colored.PendingOrange
import com.epmedu.animeal.foundation.theme.CustomColor
import com.epmedu.animeal.resources.R

enum class FeedStatus(
    @StringRes val titleId: Int,
    val icon: ImageVector,
    val color: Color
) {
    Fed(
        titleId = R.string.feed_status_fed,
        icon = AnimealIcons.Colored.FaceHappy,
        color = CustomColor.StatusGreen
    ),
    InProgress(
        titleId = R.string.feed_status_in_progress,
        icon = AnimealIcons.Colored.FaceNeutral,
        color = CustomColor.StatusYellow
    ),
    Pending(
        titleId = R.string.feed_status_pending,
        icon = AnimealIcons.Colored.PendingOrange,
        color = CustomColor.StatusYellow
    ),
    Starved(
        titleId = R.string.feed_status_starved,
        icon = AnimealIcons.Colored.FaceUpset,
        color = CustomColor.StatusRed
    )
}

fun AnimalState.toFeedStatus(): FeedStatus =
    when (this) {
        AnimalState.Fed -> FeedStatus.Fed
        AnimalState.InProgress -> FeedStatus.InProgress
        AnimalState.Pending -> FeedStatus.Pending
        AnimalState.Starved -> FeedStatus.Starved
    }