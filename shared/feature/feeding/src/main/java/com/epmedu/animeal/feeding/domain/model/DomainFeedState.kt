package com.epmedu.animeal.feeding.domain.model

import com.epmedu.animeal.feeding.presentation.model.FeedingPointModel
import com.epmedu.animeal.feeding.presentation.viewmodel.FeedingConfirmationState

data class DomainFeedState(
    val feedPoint: FeedingPointModel?,
    val feedingConfirmationState: FeedingConfirmationState,
)