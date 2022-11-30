package com.epmedu.animeal.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.component.BuildConfigProvider
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultEventDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.EventDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.extensions.StableList
import com.epmedu.animeal.feeding.domain.repository.FeedingPointRepository
import com.epmedu.animeal.feeding.presentation.model.FeedingPointModel
import com.epmedu.animeal.feeding.presentation.model.MapLocation
import com.epmedu.animeal.geolocation.gpssetting.GpsSettingsProvider
import com.epmedu.animeal.geolocation.location.LocationProvider
import com.epmedu.animeal.home.presentation.HomeScreenEvent
import com.epmedu.animeal.home.presentation.model.GpsSettingState
import com.epmedu.animeal.home.presentation.model.WillFeedState
import dagger.hilt.android.lifecycle.HiltViewModel
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

    init {
        initialize()
        fetchLocationUpdates()
        fetchFeedingPoints()
        // fetchGpsSettingsUpdates()
    }

    fun handleEvents(event: HomeScreenEvent) = when (event) {
        is HomeScreenEvent.FeedingPointSelected -> selectFeedingPoint(event)
        is HomeScreenEvent.FeedingPointFavouriteChange -> changeFavouriteFeedingPoint(event)
        is HomeScreenEvent.UserCurrentGeolocationRequest -> changeGpsSetting()
        is HomeScreenEvent.ShowWillFeedDialog -> showWillFeedDialog()
        is HomeScreenEvent.DismissWillFeedDialog -> dismissWillFeedDialog()
    }

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
                updateState { copy(currentLocation = MapLocation(it)) }
            }
        }
    }

    /*private fun fetchGpsSettingsUpdates() {
        viewModelScope.launch {
            // TODO: Fix crash
            // gpsSettingsProvider.fetchUpdates().collect(::collectGpsSettings)
        }
    }*/

    @Suppress("UnusedPrivateMember")
    private fun collectGpsSettings(state: GpsSettingsProvider.GpsSettingState) {
        val uiGpsState = when (state) {
            GpsSettingsProvider.GpsSettingState.Enabled -> GpsSettingState.Enabled
            GpsSettingsProvider.GpsSettingState.Disabled -> GpsSettingState.Disabled
        }
        updateState { copy(gpsSettingState = uiGpsState) }
    }

    private fun changeGpsSetting() = gpsSettingsProvider.changeGpsSettings()

    private fun fetchFeedingPoints() {
        viewModelScope.launch {
            feedingPointRepository.getAllFeedingPoints().collect {
                updateState {
                    copy(
                        feedingPoints = StableList(
                            it.take(15).map { feedingPoint ->
                                FeedingPointModel(
                                    feedingPoint
                                )
                            }
                        )
                    )
                }
            }
        }
    }

    private fun selectFeedingPoint(event: HomeScreenEvent.FeedingPointSelected) {
        viewModelScope.launch {
            feedingPointRepository.getFeedingPoint(event.id).collect { feedingPoint ->
                updateState {
                    copy(currentFeedingPoint = feedingPoint)
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

    private fun showWillFeedDialog() {
        updateState { copy(willFeedState = WillFeedState(true)) }
    }

    private fun dismissWillFeedDialog() {
        updateState { copy(willFeedState = WillFeedState(false)) }
    }
}