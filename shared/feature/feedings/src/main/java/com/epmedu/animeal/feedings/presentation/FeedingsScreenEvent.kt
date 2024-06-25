package com.epmedu.animeal.feedings.presentation

import com.epmedu.animeal.feedings.presentation.model.FeedingModel
import com.epmedu.animeal.feedings.presentation.viewmodel.FeedingFilterCategory

sealed interface FeedingsScreenEvent {
    data class UpdateCurrentFeeding(val feeding: FeedingModel?) : FeedingsScreenEvent
    data class UpdateCategoryEvent(val category: FeedingFilterCategory) : FeedingsScreenEvent
    data class ApproveClicked(val feeding: FeedingModel) : FeedingsScreenEvent
    data object ErrorShown : FeedingsScreenEvent
}