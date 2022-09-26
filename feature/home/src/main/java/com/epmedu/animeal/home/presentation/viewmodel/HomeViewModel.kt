package com.epmedu.animeal.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.component.BuildConfigProvider
import com.epmedu.animeal.common.component.GpsSettingsProvider
import com.epmedu.animeal.common.component.LocationProvider
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultEventDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.EventDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.home.data.FeedingPointRepository
import com.epmedu.animeal.home.presentation.HomeScreenEvent
import com.epmedu.animeal.home.presentation.model.FeedingPointUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val feedingPointRepository: FeedingPointRepository,
    private val buildConfigProvider: BuildConfigProvider,
    private val locationProvider: LocationProvider,
    private val gpsSettingsProvider: GpsSettingsProvider,
) : ViewModel(),
    StateDelegate<HomeState> by DefaultStateDelegate(initialState = HomeState()),
    EventDelegate<HomeViewModelEvent> by DefaultEventDelegate() {

    private val feedingPointsState: DefaultStateDelegate<FeedingPointsState> =
        DefaultStateDelegate(FeedingPointsState(emptyList()))

    init {
        initialize()
        fetchLocationUpdates()
        fetchFeedingPoints()
        fetchGpsSettingsUpdates()
    }

    fun handleEvents(event: HomeScreenEvent) = when (event) {
        is HomeScreenEvent.FeedingPointSelected -> {
            selectFeedingPoint(event)
        }

        is HomeScreenEvent.FeedingPointFavouriteChange -> {
            changeFavouriteFeedingPoint(event)
        }

        is HomeScreenEvent.UserCurrentGeolocationRequest -> {
            changeGpsSetting()
        }
    }

    fun getFeedingPointsFlow(): StateFlow<FeedingPointsState> = feedingPointsState.stateFlow

    private fun initialize() {
        updateState {
            copy(
                mapBoxPublicKey = buildConfigProvider.mapBoxPublicKey,
                mapBoxStyleUri = buildConfigProvider.mapBoxStyleURI
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

    private fun fetchGpsSettingsUpdates() {
        viewModelScope.launch {
            gpsSettingsProvider.fetchUpdates().collect {
                updateState { copy(gpsSettingState = it) }
            }
        }
    }

    private fun changeGpsSetting() = gpsSettingsProvider.changeGpsSettings()

    private fun fetchFeedingPoints() {
        viewModelScope.launch {
            feedingPointRepository.getAllFeedingPoints().collect {
                feedingPointsState.updateState {
                    FeedingPointsState(
                        it.take(15)
                            .map { feedingPoint -> FeedingPointUi(feedingPoint) }
                    )
                }
            }
        }
    }

    private fun selectFeedingPoint(event: HomeScreenEvent.FeedingPointSelected) {
        viewModelScope.launch {
            feedingPointRepository.getFeedingPoint(event.id).collect { feedingPoint ->
                updateState {
                    copy(currentFeedingPoint = FeedingPointUi(feedingPoint))
                }
                sendEvent(HomeViewModelEvent.ShowCurrentFeedingPoint)
            }
        }
    }

    private fun changeFavouriteFeedingPoint(event: HomeScreenEvent.FeedingPointFavouriteChange) {
        updateState {
            copy(
                currentFeedingPoint = currentFeedingPoint?.copy(isFavourite = event.isFavourite)
            )
        }
    }
}