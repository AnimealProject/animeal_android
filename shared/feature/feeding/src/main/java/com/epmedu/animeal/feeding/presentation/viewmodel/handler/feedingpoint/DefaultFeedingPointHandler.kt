package com.epmedu.animeal.feeding.presentation.viewmodel.handler.feedingpoint

import androidx.lifecycle.SavedStateHandle
import com.epmedu.animeal.common.presentation.viewmodel.delegate.ActionDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.handler.error.ErrorHandler
import com.epmedu.animeal.feeding.domain.usecase.AddFeedingPointToFavouritesUseCase
import com.epmedu.animeal.feeding.domain.usecase.GetAllFeedingPointsUseCase
import com.epmedu.animeal.feeding.domain.usecase.GetApprovedFeedingHistoriesUseCase
import com.epmedu.animeal.feeding.domain.usecase.GetFeedingInProgressUseCase
import com.epmedu.animeal.feeding.domain.usecase.GetFeedingPointByIdUseCase
import com.epmedu.animeal.feeding.domain.usecase.GetFeedingPointByPriorityUseCase
import com.epmedu.animeal.feeding.domain.usecase.RemoveFeedingPointFromFavouritesUseCase
import com.epmedu.animeal.feeding.domain.usecase.UpdateAnimalTypeSettingsUseCase
import com.epmedu.animeal.feeding.presentation.event.FeedingPointEvent
import com.epmedu.animeal.feeding.presentation.event.FeedingPointEvent.AnimalTypeChange
import com.epmedu.animeal.feeding.presentation.event.FeedingPointEvent.Deselect
import com.epmedu.animeal.feeding.presentation.event.FeedingPointEvent.FavouriteChange
import com.epmedu.animeal.feeding.presentation.event.FeedingPointEvent.Select
import com.epmedu.animeal.feeding.presentation.model.FeedStatus
import com.epmedu.animeal.feeding.presentation.model.Feeding
import com.epmedu.animeal.feeding.presentation.model.FeedingPointModel
import com.epmedu.animeal.feeding.presentation.model.MapLocation
import com.epmedu.animeal.feeding.presentation.util.Keys.SAVED_FEEDING_POINT_KEY
import com.epmedu.animeal.feeding.presentation.viewmodel.FeedingPointState
import com.epmedu.animeal.foundation.tabs.model.AnimalType
import com.epmedu.animeal.router.presentation.FeedingRouteState.Active
import com.epmedu.animeal.router.presentation.FeedingRouteState.Disabled
import com.epmedu.animeal.router.presentation.RouteHandler
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

class DefaultFeedingPointHandler @Inject constructor(
    stateDelegate: StateDelegate<FeedingPointState>,
    actionDelegate: ActionDelegate,
    routeHandler: RouteHandler,
    errorHandler: ErrorHandler,
    private val savedStateHandle: SavedStateHandle,
    private val getFeedingPointByPriorityUseCase: GetFeedingPointByPriorityUseCase,
    private val getAllFeedingPointsUseCase: GetAllFeedingPointsUseCase,
    private val getFeedingPointByIdUseCase: GetFeedingPointByIdUseCase,
    private val getFeedingHistoriesUseCase: GetApprovedFeedingHistoriesUseCase,
    private val getFeedingInProgressUseCase: GetFeedingInProgressUseCase,
    private val addFeedingPointToFavouritesUseCase: AddFeedingPointToFavouritesUseCase,
    private val removeFeedingPointFromFavouritesUseCase: RemoveFeedingPointFromFavouritesUseCase,
    private val updateAnimalTypeSettingsUseCase: UpdateAnimalTypeSettingsUseCase,
) : FeedingPointHandler,
    StateDelegate<FeedingPointState> by stateDelegate,
    ActionDelegate by actionDelegate,
    RouteHandler by routeHandler,
    ErrorHandler by errorHandler {

    private var fetchFeedingPointsJob: Job? = null
    private var fetchFeedingsJob: Job? = null
    private var fetchByPriorityJob: Job? = null

    override var feedingPointStateFlow: StateFlow<FeedingPointState> = stateFlow

    override fun CoroutineScope.restoreSavedFeedingPoint() {
        val savedFeedingPoint: FeedingPointModel? = savedStateHandle[SAVED_FEEDING_POINT_KEY]
        savedFeedingPoint?.let { selectFeedingPoint(savedFeedingPoint) }
    }

    override fun updateAnimalType(animalType: AnimalType) {
        updateState {
            copy(
                defaultAnimalType = animalType
            )
        }
    }

    override fun CoroutineScope.fetchFeedingPoints() {
        fetchFeedingPointsJob?.cancel()
        fetchFeedingPointsJob = launch {
            getAllFeedingPointsUseCase(type = state.defaultAnimalType).collect { domainFeedingPoints ->
                val feedingPoints = domainFeedingPoints.map { domainFeedingPoint ->
                    FeedingPointModel(domainFeedingPoint)
                }
                val currentFeedingPoint =
                    feedingPoints.find { it.id == state.currentFeedingPoint?.id }
                updateState {
                    copy(
                        currentFeedingPoint = getCurrentFeedingPointToShow(currentFeedingPoint),
                        feedingPoints = getFeedingPointsToShow(feedingPoints, currentFeedingPoint)
                    )
                }
            }
        }
    }

    private fun getFeedingPointsToShow(
        feedingPoints: List<FeedingPointModel>,
        currentFeedingPoint: FeedingPointModel?
    ): ImmutableList<FeedingPointModel> {
        return when (feedingRouteStateFlow.value) {
            is Active -> {
                currentFeedingPoint?.let {
                    persistentListOf(currentFeedingPoint)
                } ?: feedingPoints.find { feedingPoint ->
                    feedingPoint.id == state.feedingPoints.firstOrNull()?.id
                }?.let { feedingPoint ->
                    persistentListOf(feedingPoint)
                } ?: persistentListOf()
            }

            is Disabled -> {
                feedingPoints.toImmutableList()
            }
        }
    }

    private fun CoroutineScope.getCurrentFeedingPointToShow(currentFeedingPoint: FeedingPointModel?) =
        when (currentFeedingPoint?.feedStatus) {
            state.currentFeedingPoint?.feedStatus -> {
                currentFeedingPoint?.copy(
                    feedings = state.currentFeedingPoint?.feedings
                )
            }

            else -> {
                currentFeedingPoint?.let { fetchFeedings(currentFeedingPoint.id) }
                currentFeedingPoint
            }
        }

    override fun CoroutineScope.fetchNearestFeedingPoint(userLocation: MapLocation) {
        fetchByPriorityJob?.cancel()
        fetchByPriorityJob = launch {
            getFeedingPointByPriorityUseCase(state.defaultAnimalType, userLocation)
                .collect { result ->
                    selectFeedingPoint(FeedingPointModel(result))
                }
        }
    }

    override fun CoroutineScope.showFeedingPoint(feedingPointId: String): FeedingPointModel? {
        return getFeedingPointByIdUseCase(feedingPointId)?.let { feedingPoint ->
            FeedingPointModel(feedingPoint).also {
                selectFeedingPoint(it)
            }
        }
    }

    override suspend fun showSingleReservedFeedingPoint(feedingPoint: FeedingPointModel) {
        val reservedFeedingPoint = feedingPoint.copy(feedStatus = FeedStatus.InProgress)
        updateState {
            copy(
                defaultAnimalType = reservedFeedingPoint.animalType,
                currentFeedingPoint = null,
                feedingPoints = persistentListOf(reservedFeedingPoint)
            )
        }
        updateAnimalTypeSettingsUseCase(reservedFeedingPoint.animalType)
    }

    override fun CoroutineScope.handleFeedingPointEvent(event: FeedingPointEvent) {
        when (event) {
            is Select -> launch { selectFeedingPoint(event.feedingPoint) }
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

    private fun CoroutineScope.selectFeedingPoint(feedingPoint: FeedingPointModel) {
        savedStateHandle[SAVED_FEEDING_POINT_KEY] = feedingPoint
        updateState { copy(currentFeedingPoint = feedingPoint) }
        fetchFeedings(feedingPoint.id)
    }

    private fun CoroutineScope.fetchFeedings(feedingPointId: String) {
        fetchFeedingsJob?.cancel()
        fetchFeedingsJob = launch {
            getFeedingInProgressUseCase(feedingPointId).combine(
                getFeedingHistoriesUseCase(feedingPointId)
            ) { feedingInProgress, feedingHistories ->
                updateState {
                    copy(
                        currentFeedingPoint = currentFeedingPoint?.copy(
                            feedings = feedingInProgress?.let {
                                listOf(Feeding.InProgress(feedingInProgress))
                            } ?: feedingHistories.firstOrNull()?.let { lastFeeder ->
                                listOf(Feeding.History(lastFeeder))
                            } ?: emptyList()
                        )
                    )
                }
            }.collect()
        }
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