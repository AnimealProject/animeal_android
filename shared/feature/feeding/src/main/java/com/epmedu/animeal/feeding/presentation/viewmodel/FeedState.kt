package com.epmedu.animeal.feeding.presentation.viewmodel

import com.epmedu.animeal.feeding.presentation.model.FeedingPointModel

data class FeedState(
    val feedPoint: FeedingPointModel? = null,
    val feedingConfirmationState: FeedingConfirmationState = FeedingConfirmationState.Dismissed(),
)
