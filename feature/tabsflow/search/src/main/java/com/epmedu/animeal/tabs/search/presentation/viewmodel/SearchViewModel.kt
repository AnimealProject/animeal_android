package com.epmedu.animeal.tabs.search.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.presentation.viewmodel.delegate.ActionDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.feeding.domain.usecase.AddFeedingPointToFavouritesUseCase
import com.epmedu.animeal.feeding.domain.usecase.GetAnimalTypeFromSettingsUseCase
import com.epmedu.animeal.feeding.domain.usecase.GetApprovedFeedingHistoriesUseCase
import com.epmedu.animeal.feeding.domain.usecase.GetFeedingInProgressUseCase
import com.epmedu.animeal.feeding.domain.usecase.RemoveFeedingPointFromFavouritesUseCase
import com.epmedu.animeal.feeding.domain.usecase.UpdateAnimalTypeSettingsUseCase
import com.epmedu.animeal.feeding.presentation.event.FeedingEvent
import com.epmedu.animeal.feeding.presentation.model.Feeding
import com.epmedu.animeal.feeding.presentation.model.FeedingPointModel
import com.epmedu.animeal.feeding.presentation.util.FeedingConstants.FEEDERS_LIMIT
import com.epmedu.animeal.feeding.presentation.util.Keys
import com.epmedu.animeal.feeding.presentation.viewmodel.handler.feeding.FeedingHandler
import com.epmedu.animeal.foundation.tabs.model.AnimalType
import com.epmedu.animeal.permissions.presentation.PermissionsEvent
import com.epmedu.animeal.permissions.presentation.handler.PermissionsHandler
import com.epmedu.animeal.tabs.search.domain.SearchFeedingPointsUseCase
import com.epmedu.animeal.tabs.search.presentation.SearchScreenEvent
import com.epmedu.animeal.tabs.search.presentation.SearchScreenEvent.AnimalTypeChange
import com.epmedu.animeal.tabs.search.presentation.SearchScreenEvent.FavouriteChange
import com.epmedu.animeal.tabs.search.presentation.SearchScreenEvent.FeedingPointHidden
import com.epmedu.animeal.tabs.search.presentation.SearchScreenEvent.FeedingPointSelected
import com.epmedu.animeal.tabs.search.presentation.SearchScreenEvent.ScreenCreated
import com.epmedu.animeal.tabs.search.presentation.SearchScreenEvent.Search
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    actionDelegate: ActionDelegate,
    private val savedStateHandle: SavedStateHandle,
    private val searchCatsFeedingPointsUseCase: SearchFeedingPointsUseCase,
    private val searchDogsFeedingPointsUseCase: SearchFeedingPointsUseCase,
    private val getFeedingHistoriesUseCase: GetApprovedFeedingHistoriesUseCase,
    private val getFeedingInProgressUseCase: GetFeedingInProgressUseCase,
    private val addFeedingPointToFavouritesUseCase: AddFeedingPointToFavouritesUseCase,
    private val removeFeedingPointFromFavouritesUseCase: RemoveFeedingPointFromFavouritesUseCase,
    private val getAnimalTypeFromSettingsUseCase: GetAnimalTypeFromSettingsUseCase,
    private val updateAnimalTypeSettingsUseCase: UpdateAnimalTypeSettingsUseCase,
    private val feedingHandler: FeedingHandler,
    private val permissionsHandler: PermissionsHandler,
) : ViewModel(),
    StateDelegate<SearchState> by DefaultStateDelegate(initialState = SearchState()),
    PermissionsHandler by permissionsHandler,
    FeedingHandler by feedingHandler,
    ActionDelegate by actionDelegate {

    private var fetchFeedingsJob: Job? = null

    init {
        restoreSavedFeedingPoint()
        viewModelScope.launch { collectFeedingPoints() }
        viewModelScope.launch { collectPermissionsState() }
        viewModelScope.launch { collectFeedState() }
        viewModelScope.launch { fetchCurrentFeeding() }
    }

    private fun restoreSavedFeedingPoint() {
        val savedFeedingPoint: FeedingPointModel? = savedStateHandle[Keys.SAVED_FEEDING_POINT_KEY]
        savedFeedingPoint?.let { selectFeedingPoint(savedFeedingPoint) }
    }

    private suspend fun collectFeedState() {
        feedingStateFlow.collectLatest { feedingState ->
            updateState {
                copy(
                    feedState = feedingState
                )
            }
        }
    }

    private fun onScreenCreated() {
        viewModelScope.launch {
            val animalType = getAnimalTypeFromSettingsUseCase()

            updateState {
                copy(animalType = animalType)
            }
        }
    }

    private suspend fun collectFeedingPoints() {
        stateFlow.collectLatest { state ->
            combine(
                searchDogsFeedingPointsUseCase(state.dogsQuery, AnimalType.Dogs),
                searchCatsFeedingPointsUseCase(state.catsQuery, AnimalType.Cats)
            ) { dogs, cats ->
                val dogsToShow = dogs.map { FeedingPointModel(it) }
                val catsToShow = cats.map { FeedingPointModel(it) }
                val feedingPointsToShow = catsToShow + dogsToShow
                val favourites = feedingPointsToShow.filter { it.isFavourite }
                val showingFeedingPoint = feedingPointsToShow.find { it.id == state.showingFeedingPoint?.id }

                val feedingPointToShow = when (showingFeedingPoint?.feedStatus) {
                    state.showingFeedingPoint?.feedStatus -> {
                        showingFeedingPoint?.copy(
                            feedings = state.showingFeedingPoint?.feedings
                        )
                    }
                    else -> {
                        showingFeedingPoint?.let { fetchFeedings(showingFeedingPoint.id) }
                        showingFeedingPoint
                    }
                }
                showingFeedingPoint?.let { fetchFeedings(showingFeedingPoint.id) }

                updateState {
                    copy(
                        catsFeedingPoints = catsToShow.toImmutableList(),
                        dogsFeedingPoints = dogsToShow.toImmutableList(),
                        favourites = favourites.toImmutableList(),
                        showingFeedingPoint = feedingPointToShow
                    )
                }
            }.collect()
        }
    }

    fun handleEvents(event: SearchScreenEvent) {
        when (event) {
            is ScreenCreated -> onScreenCreated()
            is FavouriteChange -> handleFavouriteChange(event)
            is FeedingPointSelected -> selectFeedingPoint(event.feedingPoint)
            is FeedingPointHidden -> updateState { copy(showingFeedingPoint = null) }
            is Search -> handleSearch(event)
            is AnimalTypeChange -> handleAnimalTypeChange(event)
        }
    }

    fun handlePermissionsEvent(event: PermissionsEvent) {
        viewModelScope.handlePermissionEvent(event)
    }

    fun handleFeedingEvent(event: FeedingEvent) {
        viewModelScope.handleFeedingEvent(event)
    }

    private fun selectFeedingPoint(feedingPoint: FeedingPointModel) {
        savedStateHandle[Keys.SAVED_FEEDING_POINT_KEY] = feedingPoint
        updateState { copy(showingFeedingPoint = feedingPoint) }
        fetchFeedings(feedingPoint.id)
        viewModelScope.launch { collectFeedState() }
    }

    private fun fetchFeedings(feedingPointId: String) {
        fetchFeedingsJob?.cancel()
        fetchFeedingsJob = viewModelScope.launch {
            getFeedingInProgressUseCase(feedingPointId).combine(
                getFeedingHistoriesUseCase(feedingPointId)
            ) { feedingInProgress, feedingHistories ->
                val feedings = listOf(
                    feedingInProgress?.let { Feeding.InProgress(feedingInProgress) }
                ) + feedingHistories.map { Feeding.History(it) }

                updateState {
                    copy(
                        showingFeedingPoint = showingFeedingPoint?.copy(
                            feedings = feedings.filterNotNull().take(FEEDERS_LIMIT)
                        )
                    )
                }
            }.collect()
        }
    }

    private suspend fun collectPermissionsState() {
        permissionsStateFlow.collect { permissionsState ->
            updateState { copy(permissionsState = permissionsState) }
        }
    }

    private fun handleSearch(event: Search) {
        when (event.animalType) {
            AnimalType.Dogs -> updateState { copy(dogsQuery = event.query) }
            AnimalType.Cats -> updateState { copy(catsQuery = event.query) }
        }
    }

    private fun handleFavouriteChange(event: FavouriteChange) {
        when {
            event.isFavourite -> addFeedingPointToFavourites(event.feedingPoint)
            else -> removeFeedingPointFromFavourites(event.feedingPoint)
        }
    }

    private fun handleAnimalTypeChange(event: AnimalTypeChange) {
        viewModelScope.launch {
            updateAnimalTypeSettingsUseCase(event.animalType)
        }
    }

    private fun addFeedingPointToFavourites(feedingPoint: FeedingPointModel) {
        markFeedingPointAsFavourite(feedingPoint)
        tryAddingFeedingPointToFavourites(feedingPoint)
    }

    private fun removeFeedingPointFromFavourites(feedingPoint: FeedingPointModel) {
        unmarkFeedingPointFromFavourites(feedingPoint)
        tryRemovingFeedingPointFromFavourites(feedingPoint)
    }

    private fun markFeedingPointAsFavourite(feedingPoint: FeedingPointModel) {
        updateState {
            copy(
                favourites = (favourites + feedingPoint).toImmutableList(),
                showingFeedingPoint = when (showingFeedingPoint) {
                    feedingPoint -> feedingPoint.copy(isFavourite = true)
                    else -> showingFeedingPoint
                }
            )
        }
    }

    private fun unmarkFeedingPointFromFavourites(feedingPoint: FeedingPointModel) {
        updateState {
            copy(
                favourites = (favourites - feedingPoint).toImmutableList(),
                showingFeedingPoint = when (showingFeedingPoint) {
                    feedingPoint -> feedingPoint.copy(isFavourite = false)
                    else -> showingFeedingPoint
                }
            )
        }
    }

    private fun tryAddingFeedingPointToFavourites(feedingPoint: FeedingPointModel) {
        viewModelScope.launch {
            performAction(
                action = { addFeedingPointToFavouritesUseCase(feedingPoint.id) },
                onError = { unmarkFeedingPointFromFavourites(feedingPoint) }
            )
        }
    }

    private fun tryRemovingFeedingPointFromFavourites(feedingPoint: FeedingPointModel) {
        viewModelScope.launch {
            performAction(
                action = { removeFeedingPointFromFavouritesUseCase(feedingPoint.id) },
                onError = { markFeedingPointAsFavourite(feedingPoint) }
            )
        }
    }
}
