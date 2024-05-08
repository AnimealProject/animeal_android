package com.epmedu.animeal.home.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.constants.Arguments.FORCED_FEEDING_POINT_ID
import com.epmedu.animeal.common.presentation.viewmodel.delegate.ActionDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.handler.error.ErrorHandler
import com.epmedu.animeal.common.presentation.viewmodel.handler.loading.LoadingHandler
import com.epmedu.animeal.feeding.presentation.event.FeedingEvent
import com.epmedu.animeal.feeding.presentation.event.FeedingPointEvent
import com.epmedu.animeal.feeding.presentation.event.FeedingPointEvent.AnimalTypeChange
import com.epmedu.animeal.feeding.presentation.viewmodel.handler.feeding.FeedingHandler
import com.epmedu.animeal.feeding.presentation.viewmodel.handler.feedingpoint.FeedingPointHandler
import com.epmedu.animeal.feedings.presentation.viewmodel.handlers.FeedingsButtonHandler
import com.epmedu.animeal.geolocation.gpssetting.GpsSettingsProvider
import com.epmedu.animeal.geolocation.location.LocationProvider
import com.epmedu.animeal.geolocation.location.model.Location
import com.epmedu.animeal.home.domain.usecases.AnimalTypeUseCase
import com.epmedu.animeal.home.presentation.HomeScreenEvent
import com.epmedu.animeal.home.presentation.HomeScreenEvent.CameraEvent
import com.epmedu.animeal.home.presentation.HomeScreenEvent.ErrorShowed
import com.epmedu.animeal.home.presentation.HomeScreenEvent.ScreenCreated
import com.epmedu.animeal.home.presentation.HomeScreenEvent.ScreenDisplayed
import com.epmedu.animeal.home.presentation.HomeScreenEvent.TimerCancellationEvent
import com.epmedu.animeal.home.presentation.viewmodel.handlers.DefaultHomeHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.camera.CameraHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.gallery.FeedingPhotoGalleryHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.location.LocationHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.timercancellation.TimerCancellationHandler
import com.epmedu.animeal.permissions.presentation.PermissionStatus
import com.epmedu.animeal.permissions.presentation.PermissionsEvent
import com.epmedu.animeal.permissions.presentation.handler.PermissionsHandler
import com.epmedu.animeal.router.presentation.RouteEvent
import com.epmedu.animeal.router.presentation.RouteHandler
import com.epmedu.animeal.timer.domain.usecase.GetTimerStateUseCase
import com.epmedu.animeal.timer.presentation.handler.TimerEvent
import com.epmedu.animeal.timer.presentation.handler.TimerHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val actionDelegate: ActionDelegate,
    private val savedStateHandle: SavedStateHandle,
    private val gpsSettingsProvider: GpsSettingsProvider,
    private val locationProvider: LocationProvider,
    private val getTimerStateUseCase: GetTimerStateUseCase,
    private val animalTypeUseCase: AnimalTypeUseCase,
    private val loadingHandler: LoadingHandler,
    private val feedingsButtonHandler: FeedingsButtonHandler,
    stateDelegate: StateDelegate<HomeState>,
    defaultHomeHandler: DefaultHomeHandler,
    permissionsHandler: PermissionsHandler,
    photoGalleryHandler: FeedingPhotoGalleryHandler
) : ViewModel(),
    ActionDelegate by actionDelegate,
    StateDelegate<HomeState> by stateDelegate,
    CameraHandler by defaultHomeHandler,
    FeedingPointHandler by defaultHomeHandler,
    RouteHandler by defaultHomeHandler,
    FeedingHandler by defaultHomeHandler,
    LocationHandler by defaultHomeHandler,
    TimerHandler by defaultHomeHandler,
    TimerCancellationHandler by defaultHomeHandler,
    PermissionsHandler by permissionsHandler,
    ErrorHandler by defaultHomeHandler,
    FeedingPhotoGalleryHandler by photoGalleryHandler {

    private val nearestFeedingJob: Job by lazy {
        viewModelScope.launch {
            fetchNearestFeedingPoint(state.locationState.location)
        }
    }
    init {
        viewModelScope.restoreSavedFeedingPoint()
        viewModelScope.launch { collectPermissionsState() }
        viewModelScope.launch {
            feedingsButtonHandler.getFeedingsButtonState().collect { feedingsButtonState ->
                updateState { copy(feedingsButtonState = feedingsButtonState) }
            }
        }
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
            ScreenCreated -> onScreenCreated()
            ScreenDisplayed -> onScreenDisplayed()
            is CameraEvent -> viewModelScope.handleCameraEvent(event)
            HomeScreenEvent.InitialLocationWasDisplayed -> confirmInitialLocationWasDisplayed()
            is HomeScreenEvent.FeedingGalleryEvent -> viewModelScope.handleGalleryEvent(event)
            HomeScreenEvent.DismissThankYouEvent -> finishFeedingProcess()
        }
    }

    private fun onScreenCreated() {
        handleForcedFeedingPoint()
        viewModelScope.launch { fetchCurrentFeeding() }
    }

    private fun onScreenDisplayed() {
        loadingHandler.hideLoading()
        viewModelScope.launch { showCurrentAnimalType() }
    }

    private suspend fun showCurrentAnimalType() {
        val animalType = animalTypeUseCase()
        handleFeedingPointEvent(AnimalTypeChange(animalType))
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
                feedingRouteStateFlow,
                stateFlow
            ) { feedingPointUpdate, feedingUpdate, feedingRouteUpdate, homeStateFlow ->
                updateState {
                    copy(
                        feedingPointState = feedingPointUpdate,
                        feedState = feedingUpdate,
                        feedingRouteState = feedingRouteUpdate,
                    )
                }

                if (feedingPointUpdate.feedingPoints.isNotEmpty()) {
                    if (homeStateFlow.locationState !is LocationState.UndefinedLocation &&
                        nearestFeedingJob.isCompleted.not() &&
                        feedingUpdate.feedPoint == null
                    ) {
                        nearestFeedingJob.start()
                    } else {
                        nearestFeedingJob.cancel()
                    }
                }
            }.collect()
        }
    }

    private fun fetchLocationUpdates() {
        viewModelScope.launch {
            locationProvider.fetchUpdates().collect(::collectLocations)
        }
    }

    private suspend fun collectPermissionsState() {
        permissionsStateFlow.collect { permissionsState ->
            if (permissionsState.geolocationPermissionStatus is PermissionStatus.Granted &&
                state.permissionsState.geolocationPermissionStatus !is PermissionStatus.Granted
            ) {
                fetchLocationUpdates()
                collectGpsSettingState()
            }

            updateState { copy(permissionsState = permissionsState) }
        }
    }

    private fun collectGpsSettingState() {
        viewModelScope.launch {
            gpsSettingsProvider.fetchGpsSettingsUpdates().collect { gpsSettingState ->
                updateState { copy(gpsSettingState = gpsSettingState) }
            }
        }
    }

    private fun handleForcedFeedingPoint() {
        viewModelScope.launch {
            val forcedFeedingPointId: String? = savedStateHandle[FORCED_FEEDING_POINT_ID]
            if (forcedFeedingPointId != null) {
                nearestFeedingJob.cancel()
                savedStateHandle[FORCED_FEEDING_POINT_ID] = null
                showFeedingPoint(forcedFeedingPointId)?.coordinates?.let {
                    collectLocations(Location(it.latitude(), it.longitude()))
                }
            }
        }
    }
}