package com.epmedu.animeal.feedings.presentation.viewmodel

import com.epmedu.animeal.feedings.presentation.model.FeedingModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class FeedingsState(
    val currentFeeding: FeedingModel? = null,
    val feedingsFiltered: ImmutableList<FeedingModel> = persistentListOf(),
    val feedingsCategory: FeedingFilterCategory = FeedingFilterCategory.PENDING,
    val hasReviewedFeedings: Boolean = false,
    val isListLoading: Boolean = false,
    val isLockedAndLoading: Boolean = false,
    val isError: Boolean = false
)