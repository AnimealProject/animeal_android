package com.epmedu.animeal.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.component.BuildConfigProvider
import com.epmedu.animeal.common.component.LocationProvider
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultEventDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.EventDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.home.data.FeedingPointRepository
import com.epmedu.animeal.home.presentation.HomeScreenEvent
import com.epmedu.animeal.home.presentation.model.FeedingPointUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val buildConfigProvider: BuildConfigProvider,
    private val locationProvider: LocationProvider,
    private val feedingPointRepository: FeedingPointRepository
) : ViewModel(),
    StateDelegate<HomeState> by DefaultStateDelegate(initialState = HomeState()),
    EventDelegate<HomeEvent> by DefaultEventDelegate() {

    init {
        initialize()
        fetchLocationUpdates()
        fetchFeedingPoints()
    }

    fun handleEvents(event: HomeScreenEvent) {
        when (event) {
            is HomeScreenEvent.FeedingPointSelected -> {
                viewModelScope.launch {
                    feedingPointRepository.getFeedingPoint(event.id).collect { feedingPoint ->
                        updateState {
                            copy(currentFeedingPoint = FeedingPointUi(feedingPoint))
                        }
                        sendEvent(HomeEvent.ShowCurrentFeedingPoint)
                    }
                }
            }
            is HomeScreenEvent.FeedingPointFavouriteChange -> {
                updateState {
                    copy(
                        currentFeedingPoint = currentFeedingPoint?.copy(isFavourite = event.isFavourite)
                    )
                }
            }
        }
    }

    private fun initialize() {
        updateState {
            copy(
                mapBoxPublicKey = buildConfigProvider.mapBoxPublicKey,
                mapBoxStyleUri = buildConfigProvider.mapBoxStyleURI,
                areFeedingPointsLoading = false
            )
        }
    }

    private fun fetchLocationUpdates() {
        viewModelScope.launch {
            locationProvider.fetchUpdates().collect {
                updateState { copy(currentLocation = it) }
            }
        }
    }

    private fun fetchFeedingPoints() {
        viewModelScope.launch {
            feedingPointRepository.getAllFeedingPoints().collect {
                updateState {
                    copy(
                        areFeedingPointsLoading = false,
                        feedingPoints = it.map { feedingPoint -> FeedingPointUi(feedingPoint) }
                    )
                }
            }
        }
    }
}