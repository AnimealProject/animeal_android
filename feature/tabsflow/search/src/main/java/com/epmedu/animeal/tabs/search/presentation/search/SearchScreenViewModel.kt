package com.epmedu.animeal.tabs.search.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.feeding.domain.model.FeedingPoint
import com.epmedu.animeal.tabs.search.domain.GetFeedingPointsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val getFeedingPointsUseCase: GetFeedingPointsUseCase
) : ViewModel(),
    StateDelegate<SearchState> by DefaultStateDelegate(initialState = SearchState()) {

    private var favouritesSnapshot: List<FeedingPoint> = emptyList()

    init {
        viewModelScope.launch {
            stateFlow.collectLatest { state ->
                getFeedingPointsUseCase(state.query).collect {
                    favouritesSnapshot = it
                    updateState { copy(feedingPoints = it.toImmutableList()) }
                }
            }
        }

    }

    fun handleEvents(event: SearchScreenEvent) {
        when (event) {
            is SearchScreenEvent.FeedSpotChanged -> {
                val showingFeedSpot = updateShowingFeedSpot(event)
                updateSnapshot(event)

                updateState {
                    copy(
                        feedingPoints = favouritesSnapshot.toImmutableList(),
                        showingFeedSpot = showingFeedSpot
                    )
                }
            }
            is SearchScreenEvent.FeedSpotSelected -> {
                updateState { copy(showingFeedSpot = feedingPoints.first { it.id == event.id }) }
            }
            SearchScreenEvent.DismissWillFeedDialog -> {
                updateState { copy(showingWillFeedDialog = false) }
            }
            is SearchScreenEvent.ShowWillFeedDialog -> {
                updateState { copy(showingWillFeedDialog = true) }
            }
            SearchScreenEvent.FeedingPointSheetHidden -> {
                updateState { copy(showingFeedSpot = null) }
            }
            is SearchScreenEvent.Search -> {
                updateState { copy(query = event.query) }
            }
        }
    }

    private fun updateShowingFeedSpot(event: SearchScreenEvent.FeedSpotChanged): FeedingPoint? {
        return if (state.showingFeedSpot?.id == event.id) {
            state.showingFeedSpot.copy(isFavourite = event.isFavorite)
        } else {
            state.showingFeedSpot
        }
    }

    private fun updateSnapshot(event: SearchScreenEvent.FeedSpotChanged) {
        favouritesSnapshot = favouritesSnapshot.map { feedingPoint ->
            if (event.id == feedingPoint.id) {
                feedingPoint.copy(isFavourite = event.isFavorite)
            } else {
                feedingPoint
            }
        }
    }
}

