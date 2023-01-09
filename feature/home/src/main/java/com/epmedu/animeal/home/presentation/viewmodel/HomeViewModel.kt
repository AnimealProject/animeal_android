package com.epmedu.animeal.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.component.BuildConfigProvider
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultEventDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.EventDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.feeding.domain.repository.FeedingPointRepository
import com.epmedu.animeal.feeding.presentation.model.FeedingPointModel
import com.epmedu.animeal.geolocation.gpssetting.GpsSettingsProvider
import com.epmedu.animeal.geolocation.location.LocationProvider
import com.epmedu.animeal.home.domain.PermissionStatus
import com.epmedu.animeal.home.domain.usecases.GetGeolocationPermissionRequestedSettingUseCase
import com.epmedu.animeal.home.domain.usecases.SaveUserAsFeederUseCase
import com.epmedu.animeal.home.domain.usecases.UpdateGeolocationPermissionRequestedSettingUseCase
import com.epmedu.animeal.home.presentation.HomeScreenEvent
import com.epmedu.animeal.home.presentation.model.GpsSettingState
import com.epmedu.animeal.home.presentation.viewmodel.handlers.DefaultHomeHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.gps.GpsHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.location.LocationHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.route.RouteHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.willfeed.WillFeedHandler
import com.epmedu.animeal.home.presentation.viewmodel.providers.HomeProviders
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val homeProviders: HomeProviders,
    private val getGeolocationPermissionRequestedSettingUseCase: GetGeolocationPermissionRequestedSettingUseCase,
    private val updateGeolocationPermissionRequestedSettingUseCase: UpdateGeolocationPermissionRequestedSettingUseCase,
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
    BuildConfigProvider by homeProviders,
    FeedingPointRepository by homeProviders,
    LocationHandler by homeProviders,
    GpsHandler by homeProviders {

    init {
        initialize()
        fetchLocationUpdates()
        fetchFeedingPoints()
    }

    fun handleEvents(event: HomeScreenEvent) = when (event) {
        is HomeScreenEvent.FeedingPointSelected -> selectFeedingPoint(event)
        is HomeScreenEvent.FeedingPointFavouriteChange -> changeFavouriteFeedingPoint(event)
        is HomeScreenEvent.RouteEvent -> {
            handleRouteEvent(event)
            when (event) {
                HomeScreenEvent.RouteEvent.FeedingRouteCancellationRequest -> fetchFeedingPoints()
                HomeScreenEvent.RouteEvent.FeedingRouteStartRequest -> hideFeedingPointsAndSaveFeeder()
                else -> {} // Other cases are processed above
            }
        }
        is HomeScreenEvent.WillFeedEvent -> handleWillFeedEvent(event)
        is HomeScreenEvent.GeolocationPermissionStatusChanged ->
            changeGeolocationPermissionStatus(event)
    }

    private fun initialize() {
        updateState {
            copy(
                mapBoxPublicKey = homeProviders.mapBoxPublicKey,
                mapBoxStyleUri = mapBoxStyleURI,
                isInitialGeolocationPermissionAsked = getGeolocationPermissionRequestedSettingUseCase(),
                gpsSettingState = when {
                    isGpsSettingsEnabled -> GpsSettingState.Enabled
                    else -> GpsSettingState.Disabled
                }
            )
        }
    }

    private fun fetchLocationUpdates() {
        viewModelScope.launch {
            fetchUpdates().collect(::collectLocations)
        }
    }

    private fun fetchFeedingPoints() {
        viewModelScope.launch {
            getAllFeedingPoints().collect {
                updateState {
                    copy(
                        feedingPoints = it.take(15)
                            .map { feedingPoint -> FeedingPointModel(feedingPoint) }
                            .toImmutableList()
                    )
                }
            }
        }
    }

    private fun selectFeedingPoint(event: HomeScreenEvent.FeedingPointSelected) {
        viewModelScope.launch {
            getFeedingPoint(event.id).collect { feedingPoint ->
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

    private fun hideAllFeedingPointsButOne(selectedFeedingPoint: FeedingPointModel) {
        updateState {
            copy(feedingPoints = persistentListOf(selectedFeedingPoint))
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
            hideAllFeedingPointsButOne(FeedingPointModel(selectedFeedingPoint))
            saveUserAsCurrentFeeder(selectedFeedingPoint.id)
        }
    }

    private fun changeGeolocationPermissionStatus(event: HomeScreenEvent.GeolocationPermissionStatusChanged) {
        if (!state.isInitialGeolocationPermissionAsked) {
            viewModelScope.launch {
                updateGeolocationPermissionRequestedSettingUseCase(true)
            }
            updateState { copy(isInitialGeolocationPermissionAsked = true) }
        }

        updateState { copy(geolocationPermissionStatus = event.status) }

        if (event.status is PermissionStatus.Granted) {
            viewModelScope.launch {
                fetchGpsSettingsUpdates().collect(::collectGpsSettings)
            }
        }
    }
}