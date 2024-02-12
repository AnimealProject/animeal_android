package com.epmedu.animeal.feedings.presentation

import com.epmedu.animeal.feedings.presentation.viewmodel.FeedingFilterCategory

sealed interface FeedingsScreenEvent {
    data class UpdateCategoryEvent(val category: FeedingFilterCategory) : FeedingsScreenEvent
}