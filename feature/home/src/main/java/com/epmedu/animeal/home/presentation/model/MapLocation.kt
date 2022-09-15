package com.epmedu.animeal.home.presentation.model

import com.epmedu.animeal.geolocation.location.model.Location

data class MapLocation(val latitude: Double = 0.0, val longitude: Double = 0.0) {

    val isInitial = latitude == 0.0 && longitude == 0.0

    constructor(location: Location) : this(location.latitude, location.longitude)
}