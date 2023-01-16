package com.epmedu.animeal.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.component.BuildConfigProvider
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultEventDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.EventDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.feeding.domain.repository.FeedingPointRepository
import com.epmedu.animeal.geolocation.gpssetting.GpsSettingsProvider
import com.epmedu.animeal.geolocation.location.LocationProvider
import com.epmedu.animeal.home.domain.PermissionStatus
import com.epmedu.animeal.home.domain.usecases.GetGeolocationPermissionRequestedSettingUseCase
import com.epmedu.animeal.home.domain.usecases.UpdateGeolocationPermissionRequestedSettingUseCase
import com.epmedu.animeal.home.presentation.HomeScreenEvent
import com.epmedu.animeal.home.presentation.model.GpsSettingState
import com.epmedu.animeal.home.presentation.viewmodel.handlers.DefaultHomeHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.feeding.FeedingHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.gps.GpsHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.location.LocationHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.route.RouteHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.willfeed.WillFeedHandler
import com.epmedu.animeal.home.presentation.viewmodel.providers.HomeProviders
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val homeProviders: HomeProviders,
    private val getGeolocationPermissionRequestedSettingUseCase: GetGeolocationPermissionRequestedSettingUseCase,
    private val updateGeolocationPermissionRequestedSettingUseCase: UpdateGeolocationPermissionRequestedSettingUseCase,
    stateDelegate: StateDelegate<HomeState>,
    defaultHomeHandler: DefaultHomeHandler
) : ViewModel(),
    StateDelegate<HomeState> by stateDelegate,
    EventDelegate<HomeViewModelEvent> by DefaultEventDelegate(),
    RouteHandler by defaultHomeHandler,
    WillFeedHandler by defaultHomeHandler,
    FeedingHandler by defaultHomeHandler,
    LocationHandler by defaultHomeHandler,
    GpsHandler by defaultHomeHandler,
    LocationProvider by homeProviders,
    GpsSettingsProvider by homeProviders,
    BuildConfigProvider by homeProviders,
    FeedingPointRepository by homeProviders {

    init {
        initialize()
        fetchLocationUpdates()
        viewModelScope.launch { fetchFeedingPoints() }
    }

    fun handleEvents(event: HomeScreenEvent) = when (event) {
        is HomeScreenEvent.FeedingPointSelected -> selectFeedingPoint(event)
        is HomeScreenEvent.FeedingPointFavouriteChange -> changeFavouriteFeedingPoint(event)
        is HomeScreenEvent.RouteEvent -> handleRouteEvent(event = event, scope = viewModelScope)
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