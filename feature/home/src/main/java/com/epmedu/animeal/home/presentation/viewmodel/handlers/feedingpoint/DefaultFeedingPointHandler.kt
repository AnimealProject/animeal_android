package com.epmedu.animeal.home.presentation.viewmodel.handlers.feedingpoint

import com.epmedu.animeal.common.presentation.viewmodel.delegate.ActionDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.EventDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.feeding.domain.usecase.AddFeedingPointToFavouritesUseCase
import com.epmedu.animeal.feeding.domain.usecase.RemoveFeedingPointFromFavouritesUseCase
import com.epmedu.animeal.feeding.presentation.model.FeedStatus
import com.epmedu.animeal.feeding.presentation.model.FeedingPointModel
import com.epmedu.animeal.foundation.tabs.model.AnimalType
import com.epmedu.animeal.home.domain.usecases.GetAllFeedingPointsUseCase
import com.epmedu.animeal.home.domain.usecases.UpdateAnimalTypeSettingsUseCase
import com.epmedu.animeal.home.presentation.HomeScreenEvent.FeedingPointEvent
import com.epmedu.animeal.home.presentation.HomeScreenEvent.FeedingPointEvent.FavouriteChange
import com.epmedu.animeal.home.presentation.HomeScreenEvent.FeedingPointEvent.Select
import com.epmedu.animeal.home.presentation.model.FeedingRouteState
import com.epmedu.animeal.home.presentation.viewmodel.HomeState
import com.epmedu.animeal.home.presentation.viewmodel.HomeViewModelEvent
import com.epmedu.animeal.home.presentation.viewmodel.handlers.error.ErrorHandler
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Suppress("LongParameterList")
internal class DefaultFeedingPointHandler(
    stateDelegate: StateDelegate<HomeState>,
    eventDelegate: EventDelegate<HomeViewModelEvent>,
    actionDelegate: ActionDelegate,
    errorHandler: ErrorHandler,
    private val getAllFeedingPointsUseCase: GetAllFeedingPointsUseCase,
    private val addFeedingPointToFavouritesUseCase: AddFeedingPointToFavouritesUseCase,
    private val removeFeedingPointFromFavouritesUseCase: RemoveFeedingPointFromFavouritesUseCase,
    private val updateAnimalTypeSettingsUseCase: UpdateAnimalTypeSettingsUseCase,
) : FeedingPointHandler,
    StateDelegate<HomeState> by stateDelegate,
    EventDelegate<HomeViewModelEvent> by eventDelegate,
    ActionDelegate by actionDelegate,
    ErrorHandler by errorHandler {

    override suspend fun fetchFeedingPoints() {
        getAllFeedingPointsUseCase(type = state.defaultAnimalType).collect { domainFeedingPoints ->
            val feedingPoints = domainFeedingPoints.map { domainFeedingPoint ->
                FeedingPointModel(domainFeedingPoint)
            }
            val currentFeedingPoint =
                feedingPoints.find { it.id == state.currentFeedingPoint?.id }
            val feedingPointsToShow = when {
                state.feedingRouteState is FeedingRouteState.Active &&
                    currentFeedingPoint != null -> persistentListOf(currentFeedingPoint)
                else -> feedingPoints.toImmutableList()
            }

            updateState {
                copy(
                    currentFeedingPoint = currentFeedingPoint,
                    feedingPoints = feedingPointsToShow
                )
            }
        }
    }

    override suspend fun showFeedingPoint(feedingPointId: String) {
        val forcedPoint = state.feedingPoints.find { it.id == feedingPointId }
            ?: throw IllegalArgumentException("No feeding point with id: $feedingPointId")
        selectFeedingPoint(Select(forcedPoint))
    }

    override fun showSingleReservedFeedingPoint(feedingPoint: FeedingPointModel) {
        val reservedFeedingPoint = feedingPoint.copy(feedStatus = FeedStatus.YELLOW)
        updateState {
            copy(
                currentFeedingPoint = reservedFeedingPoint,
                feedingPoints = persistentListOf(reservedFeedingPoint)
            )
        }
    }

    override fun CoroutineScope.handleFeedingPointEvent(event: FeedingPointEvent) {
        when (event) {
            is Select -> launch { selectFeedingPoint(event) }
            is FavouriteChange -> launch { handleFavouriteChange(event) }
            is FeedingPointEvent.AnimalTypeChange -> launch { handleAnimalTypeChange(event.type) }
        }
    }

    private suspend fun selectFeedingPoint(event: Select) {
        updateState { copy(currentFeedingPoint = event.feedingPoint) }
        sendEvent(HomeViewModelEvent.ShowCurrentFeedingPoint)
    }

    private suspend fun handleAnimalTypeChange(type: AnimalType) {
        updateState {
            copy(defaultAnimalType = type)
        }
        updateAnimalTypeSettingsUseCase(type)
        fetchFeedingPoints()
    }

    private suspend fun handleFavouriteChange(event: FavouriteChange) {
        state.currentFeedingPoint?.let { feedingPoint ->
            changeFavouriteState(event.isFavourite, feedingPoint)
            tryModifyingFavourites(event.isFavourite, feedingPoint)
        }
    }

    private fun changeFavouriteState(
        isFavourite: Boolean,
        feedingPoint: FeedingPointModel
    ) {
        updateState {
            copy(
                currentFeedingPoint = when (currentFeedingPoint) {
                    feedingPoint -> feedingPoint.copy(isFavourite = isFavourite)
                    else -> currentFeedingPoint
                }
            )
        }
    }

    private suspend fun tryModifyingFavourites(
        isFavourite: Boolean,
        feedingPoint: FeedingPointModel
    ) {
        performAction(
            action = {
                when {
                    isFavourite -> addFeedingPointToFavouritesUseCase(feedingPoint.id)
                    else -> removeFeedingPointFromFavouritesUseCase(feedingPoint.id)
                }
            },
            onError = {
                changeFavouriteState(!isFavourite, feedingPoint)
                showError()
            }
        )
    }
}