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
    GREEN(R.string.feed_status_green, R.drawable.ic_face_happy, CustomColor.StatusGreen),
    RED(R.string.feed_status_red, R.drawable.ic_face_upset, CustomColor.StatusRed);
}

fun AnimalState.toFeedStatus(): FeedStatus =
    when (this) {
        AnimalState.GREEN -> FeedStatus.GREEN
        AnimalState.RED -> FeedStatus.RED
    }