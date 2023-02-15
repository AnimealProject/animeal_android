package com.epmedu.animeal.home.presentation.viewmodel.handlers.feedingpoint

import com.epmedu.animeal.feeding.presentation.model.FeedingPointModel
import com.epmedu.animeal.home.presentation.HomeScreenEvent.FeedingPointEvent
import kotlinx.coroutines.CoroutineScope

internal interface FeedingPointHandler {

    suspend fun fetchFeedingPoints()

    fun hideOtherFeedingPoints(feedingPoint: FeedingPointModel)

    fun CoroutineScope.handleFeedingPointEvent(event: FeedingPointEvent)
}