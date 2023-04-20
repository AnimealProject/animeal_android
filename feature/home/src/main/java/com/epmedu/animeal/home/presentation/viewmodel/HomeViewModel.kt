package com.epmedu.animeal.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.component.BuildConfigProvider
import com.epmedu.animeal.common.presentation.viewmodel.delegate.EventDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.geolocation.gpssetting.GpsSettingsProvider
import com.epmedu.animeal.geolocation.location.LocationProvider
import com.epmedu.animeal.home.domain.PermissionStatus
import com.epmedu.animeal.home.domain.usecases.AnimalTypeUseCase
import com.epmedu.animeal.home.domain.usecases.GetCameraPermissionRequestedUseCase
import com.epmedu.animeal.home.domain.usecases.GetGeolocationPermissionRequestedSettingUseCase
import com.epmedu.animeal.home.domain.usecases.UpdateCameraPermissionRequestUseCase
import com.epmedu.animeal.home.domain.usecases.UpdateGeolocationPermissionRequestedSettingUseCase
import com.epmedu.animeal.home.presentation.HomeScreenEvent
import com.epmedu.animeal.home.presentation.HomeScreenEvent.CameraPermissionAsked
import com.epmedu.animeal.home.presentation.HomeScreenEvent.CameraPermissionStatusChanged
import com.epmedu.animeal.home.presentation.HomeScreenEvent.ErrorShowed
import com.epmedu.animeal.home.presentation.HomeScreenEvent.FeedingEvent
import com.epmedu.animeal.home.presentation.HomeScreenEvent.FeedingPointEvent
import com.epmedu.animeal.home.presentation.HomeScreenEvent.GeolocationPermissionAsked
import com.epmedu.animeal.home.presentation.HomeScreenEvent.GeolocationPermissionStatusChanged
import com.epmedu.animeal.home.presentation.HomeScreenEvent.RouteEvent
import com.epmedu.animeal.home.presentation.HomeScreenEvent.TimerCancellationEvent
import com.epmedu.animeal.home.presentation.HomeScreenEvent.TimerEvent
import com.epmedu.animeal.home.presentation.HomeScreenEvent.WillFeedEvent
import com.epmedu.animeal.home.presentation.model.GpsSettingState
import com.epmedu.animeal.home.presentation.viewmodel.handlers.DefaultHomeHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.error.ErrorHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.feeding.FeedingHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.feedingpoint.FeedingPointHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.gps.GpsHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.location.LocationHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.route.RouteHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.timer.TimerHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.timercancellation.TimerCancellationHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.willfeed.WillFeedHandler
import com.epmedu.animeal.home.presentation.viewmodel.providers.HomeProviders
import com.epmedu.animeal.timer.domain.usecase.GetTimerStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("LongParameterList")
@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val homeProviders: HomeProviders,
    private val getGeolocationPermissionRequestedSettingUseCase: GetGeolocationPermissionRequestedSettingUseCase,
    private val updateGeolocationPermissionRequestedSettingUseCase: UpdateGeolocationPermissionRequestedSettingUseCase,
    private val getCameraPermissionRequestedUseCase: GetCameraPermissionRequestedUseCase,
    private val updateCameraPermissionRequestUseCase: UpdateCameraPermissionRequestUseCase,
    private val getTimerStateUseCase: GetTimerStateUseCase,
    private val animalTypeUseCase: AnimalTypeUseCase,
    stateDelegate: StateDelegate<HomeState>,
    eventDelegate: EventDelegate<HomeViewModelEvent>,
    defaultHomeHandler: DefaultHomeHandler
) : ViewModel(),
    StateDelegate<HomeState> by stateDelegate,
    EventDelegate<HomeViewModelEvent> by eventDelegate,
    FeedingPointHandler by defaultHomeHandler,
    RouteHandler by defaultHomeHandler,
    WillFeedHandler by defaultHomeHandler,
    FeedingHandler by defaultHomeHandler,
    LocationHandler by defaultHomeHandler,
    TimerHandler by defaultHomeHandler,
    TimerCancellationHandler by defaultHomeHandler,
    GpsHandler by defaultHomeHandler,
    ErrorHandler by defaultHomeHandler,
    LocationProvider by homeProviders,
    GpsSettingsProvider by homeProviders,
    BuildConfigProvider by homeProviders {

    init {
        initialize()
        fetchLocationUpdates()
        viewModelScope.launch { fetchFeedingPoints() }
        viewModelScope.launch { fetchCurrentFeeding() }
        viewModelScope.launch { getTimerState() }
    }

    private suspend fun getTimerState() {
        getTimerStateUseCase().collect {
            updateState {
                copy(timerState = it)
            }
        }
    }

    fun handleEvents(event: HomeScreenEvent) {
        when (event) {
            is FeedingPointEvent -> viewModelScope.handleFeedingPointEvent(event)
            is FeedingEvent -> viewModelScope.handleFeedingEvent(event)
            is RouteEvent -> handleRouteEvent(event = event)
            is WillFeedEvent -> handleWillFeedEvent(event)
            is GeolocationPermissionStatusChanged -> changeGeolocationPermissionStatus(event)
            GeolocationPermissionAsked -> markGeolocationPermissionAsAsked()
            is TimerEvent -> viewModelScope.handleTimerEvent(event)
            is TimerCancellationEvent -> viewModelScope.handleTimerCancellationEvent(event)
            is ErrorShowed -> hideError()
            is CameraPermissionStatusChanged -> changeCameraPermissionStatus(event)
            CameraPermissionAsked -> markCameraPermissionAsAsked()
        }
    }

    private fun initialize() {
        viewModelScope.launch {
            val defaultAnimalType = animalTypeUseCase()
            updateState {
                copy(
                    mapBoxPublicKey = homeProviders.mapBoxPublicKey,
                    mapBoxStyleUri = mapBoxStyleURI,
                    isInitialGeolocationPermissionAsked = getGeolocationPermissionRequestedSettingUseCase(),
                    gpsSettingState = when {
                        isGpsSettingsEnabled -> GpsSettingState.Enabled
                        else -> GpsSettingState.Disabled
                    },
                    defaultAnimalType = defaultAnimalType,
                    isCameraPermissionAsked = getCameraPermissionRequestedUseCase(),
                )
            }
        }
    }

    private fun fetchLocationUpdates() {
        viewModelScope.launch {
            fetchUpdates().collect(::collectLocations)
        }
    }

    private fun changeGeolocationPermissionStatus(event: GeolocationPermissionStatusChanged) {
        updateState { copy(geolocationPermissionStatus = event.status) }

        if (event.status is PermissionStatus.Granted) {
            viewModelScope.launch {
                fetchGpsSettingsUpdates().collect(::collectGpsSettings)
            }
        }
    }

    private fun markGeolocationPermissionAsAsked() {
        if (!state.isInitialGeolocationPermissionAsked) {
            viewModelScope.launch {
                updateGeolocationPermissionRequestedSettingUseCase(true)
            }
            updateState { copy(isInitialGeolocationPermissionAsked = true) }
        }
    }

    private fun markCameraPermissionAsAsked() {
        if (!state.isCameraPermissionAsked) {
            viewModelScope.launch {
                updateCameraPermissionRequestUseCase(true)
            }
            updateState { copy(isCameraPermissionAsked = true) }
        }
    }

    private fun changeCameraPermissionStatus(event: CameraPermissionStatusChanged) {
        updateState { copy(cameraPermissionStatus = event.status) }
    }
}