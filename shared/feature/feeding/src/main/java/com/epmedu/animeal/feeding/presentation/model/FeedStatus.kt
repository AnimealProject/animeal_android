package com.epmedu.animeal.feeding.presentation.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.epmedu.animeal.feeding.domain.model.enum.AnimalState
import com.epmedu.animeal.foundation.theme.CustomColor
import com.epmedu.animeal.resources.R

enum class FeedStatus(
    @StringRes val titleId: Int,
    @DrawableRes val iconId: Int,
    val color: Color
) {
    Fed(R.string.feed_status_fed, R.drawable.ic_face_happy, CustomColor.StatusGreen),
    InProgress(R.string.feed_status_in_progress, R.drawable.ic_face_neutral, CustomColor.StatusYellow),
    Pending(R.string.feed_status_pending, R.drawable.ic_pending_orange, CustomColor.StatusYellow),
    Starved(R.string.feed_status_starved, R.drawable.ic_face_upset, CustomColor.StatusRed)
}

fun AnimalState.toFeedStatus(): FeedStatus =
    when (this) {
        AnimalState.Fed -> FeedStatus.Fed
        AnimalState.InProgress -> FeedStatus.InProgress
        AnimalState.Pending -> FeedStatus.Pending
        AnimalState.Starved -> FeedStatus.Starved
    }