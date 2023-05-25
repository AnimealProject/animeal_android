package com.epmedu.animeal.feeding.presentation.viewmodel

import com.epmedu.animeal.feeding.presentation.model.FeedingPointModel
import com.epmedu.animeal.foundation.tabs.model.AnimalType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class FeedingPointState(
    val currentFeedingPoint: FeedingPointModel? = null,
    val feedingPoints: ImmutableList<FeedingPointModel> = persistentListOf(),
    val defaultAnimalType: AnimalType = AnimalType.Dogs,
)