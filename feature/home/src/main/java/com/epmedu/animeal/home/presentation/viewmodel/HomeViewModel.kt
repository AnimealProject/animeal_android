package com.epmedu.animeal.home.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.constants.Arguments.FORCED_FEEDING_POINT_ID
import com.epmedu.animeal.common.presentation.viewmodel.HomeViewModelEvent
import com.epmedu.animeal.common.presentation.viewmodel.delegate.ActionDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.EventDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.handler.error.ErrorHandler
import com.epmedu.animeal.feeding.presentation.event.FeedingEvent
import com.epmedu.animeal.feeding.presentation.event.FeedingPointEvent
import com.epmedu.animeal.feeding.presentation.viewmodel.handler.feeding.FeedingHandler
import com.epmedu.animeal.feeding.presentation.viewmodel.handler.feedingpoint.FeedingPointHandler
import com.epmedu.animeal.geolocation.gpssetting.GpsSettingsProvider
import com.epmedu.animeal.geolocation.location.LocationProvider
import com.epmedu.animeal.geolocation.location.model.Location
import com.epmedu.animeal.home.domain.usecases.AnimalTypeUseCase
import com.epmedu.animeal.home.presentation.HomeScreenEvent
import com.epmedu.animeal.home.presentation.HomeScreenEvent.CameraEvent
import com.epmedu.animeal.home.presentation.HomeScreenEvent.ErrorShowed
import com.epmedu.animeal.home.presentation.HomeScreenEvent.ScreenDisplayed
import com.epmedu.animeal.home.presentation.HomeScreenEvent.TimerCancellationEvent
import com.epmedu.animeal.home.presentation.model.GpsSettingState
import com.epmedu.animeal.home.presentation.viewmodel.handlers.DefaultHomeHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.camera.CameraHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.gallery.FeedingPhotoGalleryHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.gps.GpsHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.location.LocationHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.timercancellation.TimerCancellationHandler
import com.epmedu.animeal.home.presentation.viewmodel.providers.HomeProviders
import com.epmedu.animeal.permissions.presentation.PermissionStatus
import com.epmedu.animeal.permissions.presentation.PermissionsEvent
import com.epmedu.animeal.permissions.presentation.handler.PermissionsHandler
import com.epmedu.animeal.router.presentation.FeedingRouteState
import com.epmedu.animeal.router.presentation.RouteEvent
import com.epmedu.animeal.router.presentation.RouteHandler
import com.epmedu.animeal.timer.domain.usecase.GetTimerStateUseCase
import com.epmedu.animeal.timer.presentation.handler.TimerEvent
import com.epmedu.animeal.timer.presentation.handler.TimerHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val actionDelegate: ActionDelegate,
    private val savedStateHandle: SavedStateHandle,
    private val homeProviders: HomeProviders,
    private val getTimerStateUseCase: GetTimerStateUseCase,
    private val animalTypeUseCase: AnimalTypeUseCase,
    stateDelegate: StateDelegate<HomeState>,
    eventDelegate: EventDelegate<HomeViewModelEvent>,
    defaultHomeHandler: DefaultHomeHandler,
    permissionsHandler: PermissionsHandler,
    photoGalleryHandler: FeedingPhotoGalleryHandler
) : ViewModel(),
    ActionDelegate by actionDelegate,
    StateDelegate<HomeState> by stateDelegate,
    EventDelegate<HomeViewModelEvent> by eventDelegate,
    CameraHandler by defaultHomeHandler,
    FeedingPointHandler by defaultHomeHandler,
    RouteHandler by defaultHomeHandler,
    FeedingHandler by defaultHomeHandler,
    LocationHandler by defaultHomeHandler,
    TimerHandler by defaultHomeHandler,
    TimerCancellationHandler by defaultHomeHandler,
    GpsHandler by defaultHomeHandler,
    PermissionsHandler by permissionsHandler,
    ErrorHandler by defaultHomeHandler,
    LocationProvider by homeProviders,
    GpsSettingsProvider by homeProviders,
    FeedingPhotoGalleryHandler by photoGalleryHandler {

    init {
        viewModelScope.launch { collectPermissionsState() }
        initialize()
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
            is RouteEvent -> handleRouteEvent(event = event)
            is TimerCancellationEvent -> viewModelScope.handleTimerCancellationEvent(event)
            is ErrorShowed -> hideError()
            ScreenDisplayed -> {
                handleForcedFeedingPoint()
                initialize()
            }
            is CameraEvent -> viewModelScope.handleCameraEvent(event)
            HomeScreenEvent.MapInteracted -> handleMapInteraction()
            HomeScreenEvent.InitialLocationWasDisplayed -> confirmInitialLocationWasDisplayed()
            is HomeScreenEvent.FeedingGalleryEvent -> viewModelScope.handleGalleryEvent(event)
            HomeScreenEvent.DismissThankYouEvent -> finishFeedingProcess()
        }
    }

    private fun finishFeedingProcess() {
        deselectFeedingPoint()
        dismissThankYouDialog()
        updateState { copy(feedingPhotos = emptyList()) }
    }

    fun handlePermissionsEvent(event: PermissionsEvent) {
        viewModelScope.handlePermissionEvent(event)
    }

    fun handleFeedingEvent(event: FeedingEvent) {
        viewModelScope.handleFeedingEvent(event)
    }

    fun handleFeedingPointEvent(event: FeedingPointEvent) {
        viewModelScope.handleFeedingPointEvent(event)
    }

    fun handleTimerEvent(event: TimerEvent) {
        viewModelScope.handleTimerEvent(event)
    }

    private fun initialize() {
        viewModelScope.launch {
            val defaultAnimalType = animalTypeUseCase()
            updateAnimalType(defaultAnimalType)
            updateState {
                copy(
                    gpsSettingState = when {
                        isGpsSettingsEnabled -> GpsSettingState.Enabled
                        else -> GpsSettingState.Disabled
                    },
                    feedingPointState = feedingPointState.copy(defaultAnimalType = defaultAnimalType)
                )
            }
        }
        viewModelScope.fetchFeedingPoints()
        viewModelScope.launch { fetchCurrentFeeding() }
        viewModelScope.launch { getTimerState() }
        viewModelScope.launch {
            combine(
                feedingPointStateFlow,
                feedingStateFlow,
                feedingRouteStateFlow
            ) { feedingPointUpdate, feedingUpdate, feedingRouteUpdate ->
                updateState {
                    copy(
                        feedingPointState = feedingPointUpdate,
                        feedState = feedingUpdate,
                        feedingRouteState = feedingRouteUpdate,
                        gpsSettingState = when {
                            isGpsSettingsEnabled -> GpsSettingState.Enabled
                            else -> GpsSettingState.Disabled
                        },
                    )
                }
            }.collect()
        }
    }

    private fun fetchLocationUpdates() {
        viewModelScope.launch {
            fetchUpdates().collect(::collectLocations)
        }
    }

    private suspend fun collectPermissionsState() {
        permissionsStateFlow.collect { permissionsState ->
            if (permissionsState.geolocationPermissionStatus is PermissionStatus.Granted &&
                state.permissionsState.geolocationPermissionStatus !is PermissionStatus.Granted
            ) {
                fetchLocationUpdates()
                updateState {
                    copy(
                        gpsSettingState = when {
                            isGpsSettingsEnabled -> GpsSettingState.Enabled
                            else -> GpsSettingState.Disabled
                        }
                    )
                }
                viewModelScope.launch {
                    fetchGpsSettingsUpdates().collect(::collectGpsSettings)
                }
            }

            updateState { copy(permissionsState = permissionsState) }
        }
    }

    private fun handleForcedFeedingPoint() {
        viewModelScope.launch {
            val forcedFeedingPointId: String? = savedStateHandle[FORCED_FEEDING_POINT_ID]
            if (forcedFeedingPointId != null) {
                savedStateHandle[FORCED_FEEDING_POINT_ID] = null
                showFeedingPoint(forcedFeedingPointId).coordinates.let {
                    collectLocations(Location(it.latitude(), it.longitude()))
                }
            }
        }
    }

    private fun handleMapInteraction() {
        viewModelScope.launch {
            if (state.feedingRouteState is FeedingRouteState.Disabled) {
                sendEvent(HomeViewModelEvent.MinimiseBottomSheet)
            }
        }
    }
}