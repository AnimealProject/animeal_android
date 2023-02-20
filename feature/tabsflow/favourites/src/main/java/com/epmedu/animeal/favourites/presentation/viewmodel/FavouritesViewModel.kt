package com.epmedu.animeal.favourites.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.favourites.domain.GetFavouriteFeedingPointsUseCase
import com.epmedu.animeal.favourites.presentation.FavouritesScreenEvent
import com.epmedu.animeal.feeding.domain.model.FeedingPoint
import com.epmedu.animeal.feeding.domain.usecase.AddFavouriteFeedingPointUseCase
import com.epmedu.animeal.feeding.domain.usecase.DeleteFavouriteFeedingPointUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class FavouritesViewModel @Inject constructor(
    private val getFavouriteFeedingPointsUseCase: GetFavouriteFeedingPointsUseCase,
    private val addFavouriteFeedingPointUseCase: AddFavouriteFeedingPointUseCase,
    private val deleteFavouriteFeedingPointUseCase: DeleteFavouriteFeedingPointUseCase
) : ViewModel(),
    StateDelegate<FavouritesState> by DefaultStateDelegate(initialState = FavouritesState()) {

    private var favouritesSnapshot: List<FeedingPoint> = emptyList()

    init {
        viewModelScope.launch {
            getFavouriteFeedingPointsUseCase().collect { feedingPoints ->
                updateState { copy(favourites = feedingPoints.toImmutableList()) }
                favouritesSnapshot = feedingPoints
            }
        }
    }

    fun handleEvents(event: FavouritesScreenEvent) {
        when (event) {
            is FavouritesScreenEvent.FeedSpotChanged -> {
                viewModelScope.launch {
                    when {
                        event.isFavorite -> addFavouriteFeedingPointUseCase(event.id)
                        else -> deleteFavouriteFeedingPointUseCase(event.id)
                    }
                }
                updateSnapshot(event)
                updateShowingFeedSpot(event)
            }
            is FavouritesScreenEvent.FeedSpotSelected -> {
                updateState { copy(showingFeedSpot = favourites.first { it.id == event.id }) }
            }
            FavouritesScreenEvent.DismissWillFeedDialog -> {
                updateState { copy(showingWillFeedDialog = false) }
            }
            is FavouritesScreenEvent.ShowWillFeedDialog -> {
                updateState { copy(showingWillFeedDialog = true) }
            }
            FavouritesScreenEvent.FeedingPointSheetHidden -> {
                updateState { copy(showingFeedSpot = null) }
            }
        }
    }

    private fun updateShowingFeedSpot(event: FavouritesScreenEvent.FeedSpotChanged) {
        if (state.showingFeedSpot?.id == event.id) {
            updateState {
                copy(showingFeedSpot = showingFeedSpot?.copy(isFavourite = event.isFavorite))
            }
        }
    }

    private fun updateSnapshot(event: FavouritesScreenEvent.FeedSpotChanged) {
        favouritesSnapshot = favouritesSnapshot.map { feedingPoint ->
            if (event.id == feedingPoint.id) {
                feedingPoint.copy(isFavourite = event.isFavorite)
            } else {
                feedingPoint
            }
        }
        updateState {
            copy(favourites = favouritesSnapshot.toImmutableList())
        }
    }
}
