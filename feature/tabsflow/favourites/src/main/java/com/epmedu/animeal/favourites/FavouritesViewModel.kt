package com.epmedu.animeal.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.favourites.data.FavouritesRepository
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
                updateState { copy(favourites = it) }
            }
        }
    }

    fun handleEvents(event: FavouritesScreenEvent) {
        when (event) {
            is FavouritesScreenEvent.FeedSpotRemove ->
                updateState { copy(favourites = favourites.filterNot { it.id == event.id }) }
            is FavouritesScreenEvent.FeedSpotSelected -> {
                // TODO Implement Feed spot click
            }
        }
    }
}