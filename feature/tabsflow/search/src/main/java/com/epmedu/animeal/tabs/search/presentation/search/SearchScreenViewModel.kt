package com.epmedu.animeal.tabs.search.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.presentation.viewmodel.delegate.ActionDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.feeding.domain.model.FeedingPoint
import com.epmedu.animeal.feeding.domain.usecase.AddFeedingPointToFavouritesUseCase
import com.epmedu.animeal.feeding.domain.usecase.RemoveFeedingPointFromFavouritesUseCase
import com.epmedu.animeal.tabs.search.domain.GetFeedingPointsUseCase
import com.epmedu.animeal.tabs.search.presentation.search.SearchScreenEvent.DismissWillFeedDialog
import com.epmedu.animeal.tabs.search.presentation.search.SearchScreenEvent.FavouriteChange
import com.epmedu.animeal.tabs.search.presentation.search.SearchScreenEvent.FeedingPointHidden
import com.epmedu.animeal.tabs.search.presentation.search.SearchScreenEvent.FeedingPointSelected
import com.epmedu.animeal.tabs.search.presentation.search.SearchScreenEvent.ShowWillFeedDialog
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    actionDelegate: ActionDelegate,
    private val getFeedingPointsUseCase: GetFeedingPointsUseCase,
    private val addFeedingPointToFavouritesUseCase: AddFeedingPointToFavouritesUseCase,
    private val removeFeedingPointFromFavouritesUseCase: RemoveFeedingPointFromFavouritesUseCase
) : ViewModel(), StateDelegate<SearchState> by DefaultStateDelegate(initialState = SearchState()),
    ActionDelegate by actionDelegate {

    init {
        viewModelScope.launch {
            stateFlow.collectLatest { state ->
                getFeedingPointsUseCase(state.query).collect { points ->
                    updateState {
                        copy(
                            feedingPoints = points.toImmutableList(),
                            favourites = points.filter { it.isFavourite }.toImmutableList()
                        )
                    }
                }
            }
        }

    }

    fun handleEvents(event: SearchScreenEvent) {
        when (event) {
            is FavouriteChange -> handleFavouriteChange(event)
            is FeedingPointSelected -> updateState { copy(showingFeedingPoint = event.feedingPoint) }
            is FeedingPointHidden -> updateState { copy(showingFeedingPoint = null) }
            is ShowWillFeedDialog -> updateState { copy(showingWillFeedDialog = true) }
            is DismissWillFeedDialog -> updateState { copy(showingWillFeedDialog = false) }
            is SearchScreenEvent.Search -> updateState { copy(query = event.query) }
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
            performAction(action = { addFeedingPointToFavouritesUseCase(feedingPoint.id) },
                onError = { unmarkFeedingPointFromFavourites(feedingPoint) })
        }
    }

    private fun tryRemovingFeedingPointFromFavourites(feedingPoint: FeedingPoint) {
        viewModelScope.launch {
            performAction(action = { removeFeedingPointFromFavouritesUseCase(feedingPoint.id) },
                onError = { markFeedingPointAsFavourite(feedingPoint) })
        }
    }
}

