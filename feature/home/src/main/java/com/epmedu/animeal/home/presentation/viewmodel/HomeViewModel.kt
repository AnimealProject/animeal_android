package com.epmedu.animeal.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.component.BuildConfigProvider
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultEventDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.EventDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.extensions.StableList
import com.epmedu.animeal.geolocation.gpssetting.GpsSettingsProvider
import com.epmedu.animeal.geolocation.location.LocationProvider
import com.epmedu.animeal.home.data.FeedingPointRepository
import com.epmedu.animeal.home.domain.GetGeolocationPermissionRequestedSettingUseCase
import com.epmedu.animeal.home.domain.SaveUserAsFeederUseCase
import com.epmedu.animeal.home.presentation.HomeScreenEvent
import com.epmedu.animeal.home.presentation.model.*
import com.epmedu.animeal.home.presentation.viewmodel.handlers.DefaultHomeHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.route.RouteHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.willfeed.WillFeedHandler
import com.epmedu.animeal.home.presentation.viewmodel.providers.HomeProviders
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val feedingPointRepository: FeedingPointRepository,
    private val homeProviders: HomeProviders,
    private val getGeolocationPermissionRequestedSettingUseCase: GetGeolocationPermissionRequestedSettingUseCase,
    private val saveUserAsFeederUseCase: SaveUserAsFeederUseCase,
    stateDelegate: StateDelegate<HomeState>,
    defaultHomeHandler: DefaultHomeHandler
) : ViewModel(),
    StateDelegate<HomeState> by stateDelegate,
    EventDelegate<HomeViewModelEvent> by DefaultEventDelegate(),
    RouteHandler by defaultHomeHandler,
    WillFeedHandler by defaultHomeHandler,
    LocationProvider by homeProviders,
    GpsSettingsProvider by homeProviders,
    BuildConfigProvider by homeProviders {

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
        is HomeScreenEvent.RouteEvent -> {
            handleRouteEvent(event)
            when (event) {
                HomeScreenEvent.RouteEvent.FeedingRouteCancellationRequest -> fetchFeedingPoints()
                HomeScreenEvent.RouteEvent.FeedingRouteStartRequest -> hideFeedingPointsAndSaveFeeder()
                else -> {}
            }
        }
        is HomeScreenEvent.WillFeedEvent -> handleWillFeedEvent(event)
    }

    private fun initialize() {
        updateState {
            copy(
                mapBoxPublicKey = homeProviders.mapBoxPublicKey,
                mapBoxStyleUri = mapBoxStyleURI,
                isInitialGeolocationPermissionAsked = getGeolocationPermissionRequestedSettingUseCase()
            )
        }
    }

    private fun fetchLocationUpdates() {
        viewModelScope.launch {
            fetchUpdates().collect {
                updateState { copy(currentLocation = MapLocation(it)) }
            }
        }
    }

    // Commented for detekt purposes
    /*private fun fetchGpsSettingsUpdates() {
        viewModelScope.launch {
            // TODO: Fix crash
            // gpsSettingsProvider.fetchUpdates().collect(::collectGpsSettings)
        }
    }*/

    /*@Suppress("UnusedPrivateMember")
    private fun collectGpsSettings(state: GpsSettingsProvider.GpsSettingState) {
        val uiGpsState = when (state) {
            GpsSettingsProvider.GpsSettingState.Enabled -> GpsSettingState.Enabled
            GpsSettingsProvider.GpsSettingState.Disabled -> GpsSettingState.Disabled
        }
        updateState { copy(gpsSettingState = uiGpsState) }
    }*/

    private fun changeGpsSetting() = changeGpsSettings()

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

    private fun hideAllFeedingPointsButOne(selectedFeedingPoint: FeedingPointUi) {
        updateState {
            copy(feedingPoints = StableList(listOf(selectedFeedingPoint)))
        }
    }

    private fun saveUserAsCurrentFeeder(feedingPointId: Int) {
        viewModelScope.launch {
            saveUserAsFeederUseCase(feedingPointId).collect { isSuccessful ->
                if (isSuccessful) {
                    updateState {
                        copy(feedingPoints = state.feedingPoints)
                    }
                }
            }
        }
    }

    private fun hideFeedingPointsAndSaveFeeder() {
        viewModelScope.launch { sendEvent(HomeViewModelEvent.StartRouteFlow) }
        state.currentFeedingPoint?.let { selectedFeedingPoint ->
            hideAllFeedingPointsButOne(FeedingPointUi(selectedFeedingPoint))
            saveUserAsCurrentFeeder(selectedFeedingPoint.id)
        }
    }
}