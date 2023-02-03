package com.epmedu.animeal.home.presentation.viewmodel.handlers.feedingpoint

import com.epmedu.animeal.common.presentation.viewmodel.delegate.EventDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.feeding.presentation.model.FeedingPointModel
import com.epmedu.animeal.home.domain.usecases.GetAllFeedingPointsUseCase
import com.epmedu.animeal.home.presentation.HomeScreenEvent.FeedingPointEvent
import com.epmedu.animeal.home.presentation.HomeScreenEvent.FeedingPointEvent.FavouriteChange
import com.epmedu.animeal.home.presentation.HomeScreenEvent.FeedingPointEvent.Select
import com.epmedu.animeal.home.presentation.viewmodel.HomeState
import com.epmedu.animeal.home.presentation.viewmodel.HomeViewModelEvent
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal class DefaultFeedingPointHandler(
    stateDelegate: StateDelegate<HomeState>,
    eventDelegate: EventDelegate<HomeViewModelEvent>,
    private val getAllFeedingPointsUseCase: GetAllFeedingPointsUseCase
) : FeedingPointHandler,
    StateDelegate<HomeState> by stateDelegate,
    EventDelegate<HomeViewModelEvent> by eventDelegate {

    override suspend fun fetchFeedingPoints() {
        getAllFeedingPointsUseCase().collect { domainFeedingPoints ->
            val feedingPoints = domainFeedingPoints.map { domainFeedingPoint ->
                FeedingPointModel(domainFeedingPoint)
            }
            updateState {
                copy(
                    currentFeedingPoint = feedingPoints.find { it.id == state.currentFeedingPoint?.id },
                    feedingPoints = feedingPoints.toImmutableList()
                )
            }
        }
    }

    override fun hideOtherFeedingPoints(feedingPoint: FeedingPointModel) {
        updateState { copy(feedingPoints = persistentListOf(feedingPoint)) }
    }

    override fun CoroutineScope.handleFeedingPointEvent(event: FeedingPointEvent) {
        when (event) {
            is Select -> launch { selectFeedingPoint(event) }
            is FavouriteChange -> changeFavouriteFeedingPoint(event)
        }
    }

    private suspend fun selectFeedingPoint(event: Select) {
        updateState { copy(currentFeedingPoint = event.feedingPoint) }
        sendEvent(HomeViewModelEvent.ShowCurrentFeedingPoint)
    }

    private fun changeFavouriteFeedingPoint(event: FavouriteChange) {
        updateState {
            copy(
                currentFeedingPoint = currentFeedingPoint?.copy(isFavourite = event.isFavourite)
            )
        }
    }
}