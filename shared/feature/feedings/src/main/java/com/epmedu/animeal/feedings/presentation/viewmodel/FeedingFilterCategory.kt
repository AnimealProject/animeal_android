package com.epmedu.animeal.feedings.presentation.viewmodel

import androidx.annotation.StringRes
import com.epmedu.animeal.resources.R

enum class FeedingFilterCategory(@StringRes val title: Int) {
    PENDING(R.string.feeding_category_pending),
    APPROVED(R.string.feeding_category_approved),
    REJECTED(R.string.feeding_category_rejected),
    OUTDATED(R.string.feeding_category_outdated)
}