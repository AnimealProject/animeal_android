package com.epmedu.animeal.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.component.BuildConfigProvider
import com.epmedu.animeal.common.component.LocationProvider
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultEventDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.EventDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.home.data.FeedSpotRepository
import com.epmedu.animeal.home.presentation.HomeScreenEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val feedSpotRepository: FeedSpotRepository,
    private val buildConfigProvider: BuildConfigProvider,
    private val locationProvider: LocationProvider
) : ViewModel(),
    StateDelegate<HomeState> by DefaultStateDelegate(initialState = HomeState()),
    EventDelegate<HomeEvent> by DefaultEventDelegate() {

    init {
        updateState {
            copy(
                mapBoxPublicKey = buildConfigProvider.mapBoxPublicKey,
                mapBoxStyleUri = buildConfigProvider.mapBoxStyleURI,
            )
        }

        viewModelScope.launch {
            locationProvider.fetchUpdates().collect {
                updateState { copy(currentLocation = it) }
            }
        }
    }

    fun handleEvents(event: HomeScreenEvent) {
        when (event) {
            is HomeScreenEvent.FeedSpotSelected -> {
                viewModelScope.launch {
                    feedSpotRepository.getFeedSpot(event.id).collect { feedSpot ->
                        updateState {
                            copy(currentFeedSpot = feedSpot)
                        }
                        sendEvent(HomeEvent.ShowCurrentFeedSpotInformation)
                    }
                }
            }
            is HomeScreenEvent.FeedSpotFavouriteChange -> {
                updateState {
                    copy(
                        currentFeedSpot = currentFeedSpot.copy(isFavourite = event.isFavourite)
                    )
                }
            }
        }
    }
}