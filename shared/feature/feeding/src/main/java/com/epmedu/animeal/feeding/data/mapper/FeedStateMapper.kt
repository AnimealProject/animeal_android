package com.epmedu.animeal.feeding.data.mapper

import com.epmedu.animeal.feeding.domain.model.DomainFeedState
import com.epmedu.animeal.feeding.presentation.viewmodel.FeedState

internal fun FeedState.toDomainFeedState(): DomainFeedState =
    DomainFeedState(
        feedPoint = this.feedPoint,
        feedingConfirmationState = this.feedingConfirmationState,
    )
