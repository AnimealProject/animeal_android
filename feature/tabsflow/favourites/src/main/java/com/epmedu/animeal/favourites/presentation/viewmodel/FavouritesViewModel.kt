package com.epmedu.animeal.favourites.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.favourites.data.FavouritesRepository
import com.epmedu.animeal.favourites.presentation.FavouritesScreenEvent
import com.epmedu.animeal.feeding.domain.model.FeedingPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class FavouritesViewModel @Inject constructor(
    private val feedSpotRepository: FavouritesRepository
) : ViewModel(),
    StateDelegate<FavouritesState> by DefaultStateDelegate(initialState = FavouritesState()) {

    init {
        viewModelScope.launch {
            feedSpotRepository.getFavouriteFeedSpots().collect {
                updateState { copy(favourites = it, favouritesSnapshot = it) }
            }
        }
    }

    fun handleEvents(event: FavouritesScreenEvent) {
        when (event) {
            is FavouritesScreenEvent.FeedSpotChanged -> {
                val showingFeedSpot = updateShowingFeedSpot(event)
                val snapshot = updateSnapshot(event)

                updateState {
                    copy(
                        favouritesSnapshot = snapshot,
                        favourites = snapshot.filter { it.isFavourite },
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

    private fun updateSnapshot(event: FavouritesScreenEvent.FeedSpotChanged): MutableList<FeedingPoint> {
        val snapshot = mutableListOf<FeedingPoint>()
        state.favouritesSnapshot.forEach { feedingPoint ->
            snapshot += if (event.id == feedingPoint.id) {
                feedingPoint.copy(isFavourite = event.isFavorite)
            } else {
                feedingPoint.copy()
            }
        }

        return snapshot
    }
}
