package com.epmedu.animeal.feedings.presentation.viewmodel

import androidx.annotation.StringRes
import com.epmedu.animeal.resources.R

enum class FeedingFilterCategory(@StringRes val title: Int) {
    PENDING(R.string.feeding_filter_pending),
    HISTORY(R.string.feeding_filter_history)
}