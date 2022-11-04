package com.epmedu.animeal.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.component.BuildConfigProvider
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultEventDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.EventDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.extensions.StableList
import com.epmedu.animeal.geolocation.gpssetting.GpsSettingsProvider
import com.epmedu.animeal.geolocation.location.LocationProvider
import com.epmedu.animeal.home.data.FeedingPointRepository
import com.epmedu.animeal.home.presentation.HomeScreenEvent
import com.epmedu.animeal.home.presentation.model.FeedingPointUi
import com.epmedu.animeal.home.presentation.model.FeedingRouteState
import com.epmedu.animeal.home.presentation.model.MapLocation
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
        is HomeScreenEvent.FeedingRouteStartRequest -> startRoute()
        is HomeScreenEvent.FeedingRouteCancellationRequest -> stopRoute()
        is HomeScreenEvent.FeedingRouteUpdateRequest -> updateRoute(event)
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

    // TODO detekt triggers too many functions(11)
    /*private fun fetchGpsSettingsUpdates() {
        viewModelScope.launch {
            // gpsSettingsProvider.fetchUpdates().collect(::collectGpsSettings)
        }
    }

    @Suppress("UnusedPrivateMember")
    private fun collectGpsSettings(state: GpsSettingsProvider.GpsSettingState) {
        val uiGpsState = when (state) {
            GpsSettingsProvider.GpsSettingState.Enabled -> GpsSettingState.Enabled
            GpsSettingsProvider.GpsSettingState.Disabled -> GpsSettingState.Disabled
        }
        updateState { copy(gpsSettingState = uiGpsState) }
    }*/

    private fun changeGpsSetting() = gpsSettingsProvider.changeGpsSettings()

    private fun fetchFeedingPoints() {
        viewModelScope.launch {
            feedingPointRepository.getAllFeedingPoints().collect {
                updateState {
                    copy(
                        feedingPoints = StableList(
                            it.take(15).map { feedingPoint -> FeedingPointUi(feedingPoint) }
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

    // Route

    private fun startRoute() {
        viewModelScope.launch {
            updateState {
                copy(feedingRouteState = FeedingRouteState(isRouteActive = true))
            }
            sendEvent(HomeViewModelEvent.StartRouteFlow)
        }
    }

    private fun stopRoute() {
        updateState { copy(feedingRouteState = FeedingRouteState()) }
    }

    private fun updateRoute(event: HomeScreenEvent.FeedingRouteUpdateRequest) {
        updateState {
            copy(
                feedingRouteState = feedingRouteState.copy(
                    isRouteActive = event.result.isSuccessful,
                    timeLeft = event.result.timeLeft,
                    distanceLeft = event.result.distanceLeft
                )
            )
        }
    }
}