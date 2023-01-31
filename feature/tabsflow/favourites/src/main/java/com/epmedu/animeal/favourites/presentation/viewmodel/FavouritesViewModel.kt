package com.epmedu.animeal.favourites.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.favourites.domain.GetFavouriteFeedingPointsUseCase
import com.epmedu.animeal.favourites.presentation.FavouritesScreenEvent
import com.epmedu.animeal.feeding.domain.model.FeedingPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class FavouritesViewModel @Inject constructor(
    private val getFavouriteFeedingPointsUseCase: GetFavouriteFeedingPointsUseCase
) : ViewModel(),
    StateDelegate<FavouritesState> by DefaultStateDelegate(initialState = FavouritesState()) {

    private var favouritesSnapshot: List<FeedingPoint> = emptyList()

    init {
        viewModelScope.launch {
            getFavouriteFeedingPointsUseCase().collect {
                favouritesSnapshot = it
                updateState { copy(favourites = it.toImmutableList()) }
            }
        }
    }

    fun handleEvents(event: FavouritesScreenEvent) {
        when (event) {
            is FavouritesScreenEvent.FeedSpotChanged -> {
                val showingFeedSpot = updateShowingFeedSpot(event)
                updateSnapshot(event)

                updateState {
                    copy(
                        favourites = favouritesSnapshot.filter { it.isFavourite }.toImmutableList(),
                        showingFeedSpot = showingFeedSpot
                    )
                }
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

    private fun updateShowingFeedSpot(event: FavouritesScreenEvent.FeedSpotChanged): FeedingPoint? {
        return if (state.showingFeedSpot?.id == event.id) {
            state.showingFeedSpot.copy(isFavourite = event.isFavorite)
        } else {
            state.showingFeedSpot
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
    }
}
