package com.epmedu.animeal.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultEventDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.EventDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.favourites.data.FavouritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class FavouritesViewModel @Inject constructor(
    private val feedSpotRepository: FavouritesRepository
) : ViewModel(),
    StateDelegate<FavouritesState> by DefaultStateDelegate(initialState = FavouritesState()),
    EventDelegate<FavouritesScreenEvent> by DefaultEventDelegate() {

    init {
        viewModelScope.launch {
            feedSpotRepository.getFavouriteFeedSpots().collect {
                updateState { copy(favourites = it) }
            }
        }
    }

    fun handleEvents(event: FavouritesScreenEvent) {
        when (event) {
            is FavouritesScreenEvent.FeedSpotChanged ->
                updateState { copy(favourites = favourites.filterNot { it.id == event.id }) }
            is FavouritesScreenEvent.FeedSpotSelected -> {
                updateState { copy(showingFeedSpot = favourites.first { it.id == event.id }) }
            }
        }
        viewModelScope.launch { sendEvent(event) }
    }
}
