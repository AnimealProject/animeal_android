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
import com.epmedu.animeal.home.domain.GetGeolocationPermissionRequestedSettingUseCase
import com.epmedu.animeal.home.presentation.HomeScreenEvent
import com.epmedu.animeal.home.presentation.RouteManager
import com.epmedu.animeal.home.presentation.model.*
import com.epmedu.animeal.profile.data.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val feedingPointRepository: FeedingPointRepository,
    private val profileRepository: ProfileRepository,
    private val buildConfigProvider: BuildConfigProvider,
    private val locationProvider: LocationProvider,
    private val gpsSettingsProvider: GpsSettingsProvider,
    private val getGeolocationPermissionRequestedSettingUseCase: GetGeolocationPermissionRequestedSettingUseCase,
) : ViewModel(),
    StateDelegate<HomeState> by DefaultStateDelegate(initialState = HomeState()),
    EventDelegate<HomeViewModelEvent> by DefaultEventDelegate() {

    private val routeManager: RouteManager = object : RouteManager {
        override fun startRoute() {
            //this@HomeViewModel::startRoute
        }

        override fun stopRoute() {
            TODO("Not yet implemented")
        }

        override fun updateRoute() {
            TODO("Not yet implemented")
        }
    }

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
        is HomeScreenEvent.FeedingRouteStartRequest -> routeManager.startRoute()
        is HomeScreenEvent.FeedingRouteCancellationRequest -> stopRoute()
        is HomeScreenEvent.FeedingRouteUpdateRequest -> updateRoute(event)
        is HomeScreenEvent.FeedingTimerUpdateRequest -> updateTimer(event)
        is HomeScreenEvent.ShowWillFeedDialog -> showWillFeedDialog()
        is HomeScreenEvent.DismissWillFeedDialog -> dismissWillFeedDialog()
    }

    private fun initialize() {
        updateState {
            copy(
                mapBoxPublicKey = buildConfigProvider.mapBoxPublicKey,
                mapBoxStyleUri = buildConfigProvider.mapBoxStyleURI,
                isInitialGeolocationPermissionAsked = getGeolocationPermissionRequestedSettingUseCase()
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

    private fun fetchGpsSettingsUpdates() {
        viewModelScope.launch {
            // TODO: Fix crash
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
    }

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

    private fun showWillFeedDialog() {
        updateState { copy(willFeedState = WillFeedState.Showing) }
    }

    private fun dismissWillFeedDialog() {
        updateState { copy(willFeedState = WillFeedState.Dismissed) }
    }

    // Route

    /*private fun startRoute() {
        dismissWillFeedDialog()
        state.currentFeedingPoint?.let { selectedFeedingPoint ->
            hideAllFeedingPointsButOne(FeedingPointUi(selectedFeedingPoint))
            saveUserAsCurrentFeeder(selectedFeedingPoint.id)
        }
        viewModelScope.launch {
            updateState {
                copy(feedingRouteState = FeedingRouteState.Started)
            }
            sendEvent(HomeViewModelEvent.StartRouteFlow)
        }
    }*/

    private fun stopRoute() {
        updateState { copy(feedingRouteState = FeedingRouteState.Disabled) }
        fetchFeedingPoints()
    }

    private fun updateRoute(event: HomeScreenEvent.FeedingRouteUpdateRequest) {
        updateState {
            copy(
                feedingRouteState = FeedingRouteState.Updated(
                    event.result.distanceLeft,
                    state.feedingRouteState.timeLeft
                )
            )
        }
    }

    private fun updateTimer(event: HomeScreenEvent.FeedingTimerUpdateRequest) {
        updateState {
            copy(
                feedingRouteState = FeedingRouteState.Updated(
                    state.feedingRouteState.distanceLeft,
                    event.timeLeft
                )
            )
        }
    }

    private fun hideAllFeedingPointsButOne(selectedFeedingPoint: FeedingPointUi) {
        updateState {
            copy(feedingPoints = StableList(listOf(selectedFeedingPoint)))
        }
    }

    private fun saveUserAsCurrentFeeder(feedingPointId: Int) {
        viewModelScope.launch {
            profileRepository.getProfile().collect { profile ->
                feedingPointRepository.saveUserAsCurrentFeeder(profile, feedingPointId).collect {
                    updateState {
                        copy(feedingPoints = state.feedingPoints)
                    }
                }
            }
        }
    }
}