package com.epmedu.animeal.home.presentation.viewmodel.handlers.location

import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.feeding.presentation.model.MapLocation
import com.epmedu.animeal.geolocation.location.model.Location
import com.epmedu.animeal.home.presentation.viewmodel.HomeState
import com.epmedu.animeal.home.presentation.viewmodel.LocationState

class DefaultLocationHandler(
    stateDelegate: StateDelegate<HomeState>
) : LocationHandler, StateDelegate<HomeState> by stateDelegate {

    override fun collectLocations(currentLocation: Location) {
        val mapLocation = MapLocation(currentLocation)

        val locationState = when (state.locationState) {
            is LocationState.ExactLocation -> LocationState.ExactLocation(mapLocation)
            else -> LocationState.InitialLocation(mapLocation)
        }
        updateState { copy(locationState = locationState) }
    }

    override fun confirmInitialLocationWasDisplayed() {
        updateState { copy(locationState = LocationState.ExactLocation(state.locationState.location)) }
    }
}