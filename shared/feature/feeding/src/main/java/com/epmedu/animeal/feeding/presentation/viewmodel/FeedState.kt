package com.epmedu.animeal.feeding.presentation.viewmodel

import com.epmedu.animeal.feeding.presentation.model.FeedingPointModel

data class FeedState(
    val feedPoint: FeedingPointModel? = null,
    // val feedingPoints: ImmutableList<FeedingPointModel> = persistentListOf(),
    val feedingConfirmationState: FeedingConfirmationState = FeedingConfirmationState.Dismissed,
    // val defaultAnimalType: AnimalType = AnimalType.Dogs,
)
