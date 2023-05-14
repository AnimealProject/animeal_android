package com.epmedu.animeal.feeding.presentation.viewmodel.handler.feedingpoint

import com.epmedu.animeal.common.presentation.viewmodel.HomeViewModelEvent
import com.epmedu.animeal.common.presentation.viewmodel.delegate.ActionDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.EventDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.handler.error.ErrorHandler
import com.epmedu.animeal.feeding.domain.usecase.AddFeedingPointToFavouritesUseCase
import com.epmedu.animeal.feeding.domain.usecase.GetAllFeedingPointsUseCase
import com.epmedu.animeal.feeding.domain.usecase.RemoveFeedingPointFromFavouritesUseCase
import com.epmedu.animeal.feeding.domain.usecase.UpdateAnimalTypeSettingsUseCase
import com.epmedu.animeal.feeding.presentation.event.FeedingPointEvent
import com.epmedu.animeal.feeding.presentation.event.FeedingPointEvent.AnimalTypeChange
import com.epmedu.animeal.feeding.presentation.event.FeedingPointEvent.Deselect
import com.epmedu.animeal.feeding.presentation.event.FeedingPointEvent.FavouriteChange
import com.epmedu.animeal.feeding.presentation.event.FeedingPointEvent.Select
import com.epmedu.animeal.feeding.presentation.model.FeedStatus
import com.epmedu.animeal.feeding.presentation.model.FeedingPointModel
import com.epmedu.animeal.feeding.presentation.viewmodel.FeedingPointState
import com.epmedu.animeal.foundation.tabs.model.AnimalType
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Suppress("LongParameterList", "TooManyFunctions")
class DefaultFeedingPointHandler(
    stateDelegate: StateDelegate<FeedingPointState>,
    eventDelegate: EventDelegate<HomeViewModelEvent>,
    actionDelegate: ActionDelegate,
    errorHandler: ErrorHandler,
    private val getAllFeedingPointsUseCase: GetAllFeedingPointsUseCase,
    private val addFeedingPointToFavouritesUseCase: AddFeedingPointToFavouritesUseCase,
    private val removeFeedingPointFromFavouritesUseCase: RemoveFeedingPointFromFavouritesUseCase,
    private val updateAnimalTypeSettingsUseCase: UpdateAnimalTypeSettingsUseCase,
) : FeedingPointHandler,
    StateDelegate<FeedingPointState> by stateDelegate,
    EventDelegate<HomeViewModelEvent> by eventDelegate,
    ActionDelegate by actionDelegate,
    ErrorHandler by errorHandler {

    private var job: Job? = null

    override var feedingPointStateFlow: StateFlow<FeedingPointState> = stateFlow
    override fun updateAnimalType(animalType: AnimalType) {
        updateState {
            copy(
                defaultAnimalType = animalType
            )
        }
    }

    override fun CoroutineScope.fetchFeedingPoints() {
        job?.cancel()
        job = launch {
            getAllFeedingPointsUseCase(type = state.defaultAnimalType).collect { domainFeedingPoints ->
                val feedingPoints = domainFeedingPoints.map { domainFeedingPoint ->
                    FeedingPointModel(domainFeedingPoint)
                }
                val currentFeedingPoint =
                    feedingPoints.find { it.id == state.currentFeedingPoint?.id }

                updateState {
                    copy(
                        currentFeedingPoint = currentFeedingPoint,
                        feedingPoints = feedingPoints.toImmutableList()
                    )
                }
            }
        }
    }

    override suspend fun showFeedingPoint(feedingPointId: String): FeedingPointModel {
        val forcedPoint = state.feedingPoints.find { it.id == feedingPointId }
            ?: throw IllegalArgumentException("No feeding point with id: $feedingPointId")
        selectFeedingPoint(Select(forcedPoint))
        return forcedPoint
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
            Deselect -> launch { deselectFeedingPoint() }
            is FavouriteChange -> launch { handleFavouriteChange(event) }
            is AnimalTypeChange -> handleAnimalTypeChange(event.type)
        }
    }

    override fun deselectFeedingPoint() {
        if (state.currentFeedingPoint != null) {
            updateState {
                copy(
                    currentFeedingPoint = null,
                )
            }
        }
    }

    private suspend fun selectFeedingPoint(event: Select) {
        updateState { copy(currentFeedingPoint = event.feedingPoint) }
        sendEvent(HomeViewModelEvent.ShowCurrentFeedingPoint)
    }

    private fun CoroutineScope.handleAnimalTypeChange(type: AnimalType) {
        updateState {
            copy(defaultAnimalType = type)
        }
        launch {
            updateAnimalTypeSettingsUseCase(type)
            fetchFeedingPoints()
        }
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