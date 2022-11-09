package com.epmedu.animeal.feeding.presentation.model

import com.epmedu.animeal.feeding.data.model.enum.AnimalState
import com.epmedu.animeal.foundation.common.FeedStatus

fun AnimalState.toFeedStatus(): FeedStatus =
    when (this) {
        AnimalState.GREEN -> FeedStatus.GREEN
        AnimalState.RED -> FeedStatus.RED
    }