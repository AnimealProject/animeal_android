package com.epmedu.animeal.tabs.search.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.presentation.viewmodel.delegate.ActionDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.feeding.domain.model.FeedingPoint
import com.epmedu.animeal.feeding.domain.usecase.AddFeedingPointToFavouritesUseCase
import com.epmedu.animeal.feeding.domain.usecase.RemoveFeedingPointFromFavouritesUseCase
import com.epmedu.animeal.feeding.presentation.event.FeedingEvent
import com.epmedu.animeal.feeding.presentation.viewmodel.handler.feeding.FeedingHandler
import com.epmedu.animeal.foundation.tabs.model.AnimalType
import com.epmedu.animeal.permissions.presentation.PermissionsEvent
import com.epmedu.animeal.permissions.presentation.handler.PermissionsHandler
import com.epmedu.animeal.tabs.search.domain.SearchCatsFeedingPointsUseCase
import com.epmedu.animeal.tabs.search.domain.SearchDogsFeedingPointsUseCase
import com.epmedu.animeal.tabs.search.presentation.SearchScreenEvent
import com.epmedu.animeal.tabs.search.presentation.SearchScreenEvent.FavouriteChange
import com.epmedu.animeal.tabs.search.presentation.SearchScreenEvent.FeedingPointHidden
import com.epmedu.animeal.tabs.search.presentation.SearchScreenEvent.FeedingPointSelected
import com.epmedu.animeal.tabs.search.presentation.SearchScreenEvent.Search
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@Suppress("TooManyFunctions")
class SearchViewModel @Inject constructor(
    actionDelegate: ActionDelegate,
    private val searchCatsFeedingPointsUseCase: SearchCatsFeedingPointsUseCase,
    private val searchDogsFeedingPointsUseCase: SearchDogsFeedingPointsUseCase,
    private val addFeedingPointToFavouritesUseCase: AddFeedingPointToFavouritesUseCase,
    private val removeFeedingPointFromFavouritesUseCase: RemoveFeedingPointFromFavouritesUseCase,
    private val feedingHandler: FeedingHandler,
    private val permissionsHandler: PermissionsHandler,
) : ViewModel(),
    StateDelegate<SearchState> by DefaultStateDelegate(initialState = SearchState()),
    PermissionsHandler by permissionsHandler,
    FeedingHandler by feedingHandler,
    ActionDelegate by actionDelegate {

    init {
        viewModelScope.launch {
            stateFlow.collectLatest { state ->
                combine(
                    searchDogsFeedingPointsUseCase(state.dogsQuery),
                    searchCatsFeedingPointsUseCase(state.catsQuery)
                ) { dogs, cats ->
                    updateState {
                        copy(
                            catsFeedingPoints = cats.toImmutableList(),
                            dogsFeedingPoints = dogs.toImmutableList(),
                            favourites = (cats + dogs).filter { it.isFavourite }.toImmutableList()
                        )
                    }
                }.collect()
            }
        }
        viewModelScope.launch { collectPermissionsState() }
        viewModelScope.launch {
            feedingStateFlow.collectLatest { feedingState ->
                updateState {
                    copy(
                        feedingPointState = feedingState
                    )
                }
            }
        }
    }

    fun handleEvents(event: SearchScreenEvent) {
        when (event) {
            is FavouriteChange -> handleFavouriteChange(event)
            is FeedingPointSelected -> updateState { copy(showingFeedingPoint = event.feedingPoint) }
            is FeedingPointHidden -> updateState { copy(showingFeedingPoint = null) }
            is Search -> handleSearch(event)
        }
    }

    fun handlePermissionsEvent(event: PermissionsEvent) {
        viewModelScope.handlePermissionEvent(event)
    }

    fun handleFeedingEvent(event: FeedingEvent) {
        viewModelScope.handleFeedingEvent(event)
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

    private fun addFeedingPointToFavourites(feedingPoint: FeedingPoint) {
        markFeedingPointAsFavourite(feedingPoint)
        tryAddingFeedingPointToFavourites(feedingPoint)
    }

    private fun removeFeedingPointFromFavourites(feedingPoint: FeedingPoint) {
        unmarkFeedingPointFromFavourites(feedingPoint)
        tryRemovingFeedingPointFromFavourites(feedingPoint)
    }

    private fun markFeedingPointAsFavourite(feedingPoint: FeedingPoint) {
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

    private fun unmarkFeedingPointFromFavourites(feedingPoint: FeedingPoint) {
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

    private fun tryAddingFeedingPointToFavourites(feedingPoint: FeedingPoint) {
        viewModelScope.launch {
            performAction(
                action = { addFeedingPointToFavouritesUseCase(feedingPoint.id) },
                onError = { unmarkFeedingPointFromFavourites(feedingPoint) }
            )
        }
    }

    private fun tryRemovingFeedingPointFromFavourites(feedingPoint: FeedingPoint) {
        viewModelScope.launch {
            performAction(
                action = { removeFeedingPointFromFavouritesUseCase(feedingPoint.id) },
                onError = { markFeedingPointAsFavourite(feedingPoint) }
            )
        }
    }
}
