package com.epmedu.animeal.feeding.presentation.viewmodel.handler.feedingpoint

import com.epmedu.animeal.feeding.presentation.event.FeedingPointEvent
import com.epmedu.animeal.feeding.presentation.model.FeedingPointModel
import com.epmedu.animeal.feeding.presentation.model.MapLocation
import com.epmedu.animeal.feeding.presentation.viewmodel.FeedingPointState
import com.epmedu.animeal.foundation.tabs.model.AnimalType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow

interface FeedingPointHandler {

    var feedingPointStateFlow: StateFlow<FeedingPointState>

    fun updateAnimalType(animalType: AnimalType)

    fun CoroutineScope.restoreSavedFeedingPoint()

    fun CoroutineScope.fetchFeedingPoints()

    fun CoroutineScope.fetchNearestFeedingPoint(userLocation: MapLocation)

    fun deselectFeedingPoint()

    fun CoroutineScope.showFeedingPoint(feedingPointId: String): FeedingPointModel?

    fun showSingleReservedFeedingPoint(feedingPoint: FeedingPointModel)

    fun CoroutineScope.handleFeedingPointEvent(event: FeedingPointEvent)
}