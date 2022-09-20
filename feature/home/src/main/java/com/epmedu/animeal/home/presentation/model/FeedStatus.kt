package com.epmedu.animeal.home.presentation.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.epmedu.animeal.foundation.theme.CustomColor
import com.epmedu.animeal.home.data.model.enum.AnimalState
import com.epmedu.animeal.resources.R

enum class FeedStatus(
    @StringRes val titleId: Int,
    @DrawableRes val iconId: Int,
    val color: Color
) {
    GREEN(R.string.feed_status_green, R.drawable.ic_face_happy, CustomColor.StatusGreen),
    ORANGE(R.string.feed_status_orange, R.drawable.ic_face_neutral, CustomColor.StatusOrange),
    RED(R.string.feed_status_red, R.drawable.ic_face_upset, CustomColor.StatusRed);

    companion object {
        fun AnimalState.toFeedStatus(): FeedStatus =
            when (this) {
                AnimalState.GREEN -> GREEN
                AnimalState.ORANGE -> ORANGE
                AnimalState.RED -> RED
            }
    }
}