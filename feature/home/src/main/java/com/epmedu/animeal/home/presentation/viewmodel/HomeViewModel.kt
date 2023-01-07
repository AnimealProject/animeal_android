package com.epmedu.animeal.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.component.BuildConfigProvider
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultEventDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.EventDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.feeding.domain.repository.FeedingPointRepository
import com.epmedu.animeal.feeding.presentation.model.FeedingPointModel
import com.epmedu.animeal.feeding.presentation.model.MapLocation
import com.epmedu.animeal.geolocation.gpssetting.GpsSettingsProvider
import com.epmedu.animeal.geolocation.location.LocationProvider
import com.epmedu.animeal.geolocation.location.model.Location
import com.epmedu.animeal.home.domain.GetGeolocationPermissionRequestedSettingUseCase
import com.epmedu.animeal.home.domain.PermissionStatus
import com.epmedu.animeal.home.domain.UpdateGeolocationPermissionRequestedSettingUseCase
import com.epmedu.animeal.home.presentation.HomeScreenEvent
import com.epmedu.animeal.home.presentation.model.GpsSettingState
import com.epmedu.animeal.home.presentation.model.WillFeedState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@Suppress("TooManyFunctions")
class HomeViewModel @Inject constructor(
    private val feedingPointRepository: FeedingPointRepository,
    private val buildConfigProvider: BuildConfigProvider,
    private val locationProvider: LocationProvider,
    private val gpsSettingsProvider: GpsSettingsProvider,
    private val getGeolocationPermissionRequestedSettingUseCase: GetGeolocationPermissionRequestedSettingUseCase,
    private val updateGeolocationPermissionRequestedSettingUseCase: UpdateGeolocationPermissionRequestedSettingUseCase,
) : ViewModel(),
    StateDelegate<HomeState> by DefaultStateDelegate(initialState = HomeState()),
    EventDelegate<HomeViewModelEvent> by DefaultEventDelegate() {

    init {
        initialize()
        fetchLocationUpdates()
        fetchFeedingPoints()
    }

    fun handleEvents(event: HomeScreenEvent) = when (event) {
        is HomeScreenEvent.FeedingPointSelected -> selectFeedingPoint(event)
        is HomeScreenEvent.FeedingPointFavouriteChange -> changeFavouriteFeedingPoint(event)
        is HomeScreenEvent.ShowWillFeedDialog -> showWillFeedDialog()
        is HomeScreenEvent.DismissWillFeedDialog -> dismissWillFeedDialog()
        is HomeScreenEvent.GeolocationPermissionStatusChanged -> changeGeolocationPermissionStatus(event)
    }

    private fun initialize() {
        updateState {
            copy(
                mapBoxPublicKey = buildConfigProvider.mapBoxPublicKey,
                mapBoxStyleUri = buildConfigProvider.mapBoxStyleURI,
                isInitialGeolocationPermissionAsked = getGeolocationPermissionRequestedSettingUseCase(),
                gpsSettingState = when {
                    gpsSettingsProvider.isGpsSettingsEnabled -> GpsSettingState.Enabled
                    else -> GpsSettingState.Disabled
                }
            )
        }
    }

    private fun fetchLocationUpdates() {
        viewModelScope.launch {
            locationProvider.fetchUpdates().collect(::collectLocations)
        }
    }

    private fun collectLocations(currentLocation: Location) {
        val mapLocation = MapLocation(currentLocation)

        val locationState = when (state.locationState) {
            is LocationState.UndefinedLocation -> LocationState.InitialLocation(mapLocation)
            else -> LocationState.ExactLocation(mapLocation)
        }

        updateState { copy(locationState = locationState) }
    }

    private fun collectGpsSettings(state: GpsSettingsProvider.GpsSettingState) {
        val uiGpsState = when (state) {
            GpsSettingsProvider.GpsSettingState.Enabled -> GpsSettingState.Enabled
            GpsSettingsProvider.GpsSettingState.Disabled -> GpsSettingState.Disabled
        }
        updateState { copy(gpsSettingState = uiGpsState) }
    }

    private fun fetchFeedingPoints() {
        viewModelScope.launch {
            feedingPointRepository.getAllFeedingPoints().collect {
                updateState {
                    copy(
                        feedingPoints = it.map { feedingPoint ->
                            FeedingPointModel(feedingPoint)
                        }.toImmutableList()
                    )
                }
            }
        }
    }

    private fun selectFeedingPoint(event: HomeScreenEvent.FeedingPointSelected) {
        viewModelScope.launch {
            feedingPointRepository.getFeedingPoint(event.id).collect { feedingPoint ->
                feedingPoint?.let {
                    updateState { copy(currentFeedingPoint = it) }
                    sendEvent(HomeViewModelEvent.ShowCurrentFeedingPoint)
                }
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
                gpsSettingsProvider.fetchUpdates().collect(::collectGpsSettings)
            }
        }
    }
}