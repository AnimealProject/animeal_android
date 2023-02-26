package com.epmedu.animeal.home.presentation.viewmodel.handlers.location

import com.epmedu.animeal.geolocation.location.model.Location

interface LocationHandler {
    fun collectLocations(currentLocation: Location)
}