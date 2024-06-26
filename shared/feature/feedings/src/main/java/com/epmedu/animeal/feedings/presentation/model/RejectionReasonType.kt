package com.epmedu.animeal.feedings.presentation.model

import androidx.annotation.StringRes
import com.epmedu.animeal.resources.R

enum class RejectionReasonType(@StringRes val text: Int) {
    NoFood(R.string.rejection_reason_no_food),
    BadPhotoQuality(R.string.rejection_reason_bad_photo_quality),
    FeedingPointNotVisible(R.string.rejection_reason_feeding_point_not_visible),
    InappropriateContent(R.string.rejection_reason_inappropriate_content),
    Other(R.string.rejection_reason_other)
}