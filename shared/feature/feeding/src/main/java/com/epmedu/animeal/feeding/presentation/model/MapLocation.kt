package com.epmedu.animeal.feeding.presentation.model

import com.epmedu.animeal.geolocation.location.model.Location
import com.mapbox.geojson.Point

data class MapLocation(val latitude: Double = 0.0, val longitude: Double = 0.0) {

    constructor(location: Location) : this(location.latitude, location.longitude)

    companion object {
        val Tbilisi = MapLocation(latitude = 41.719545681547245, longitude = 44.78956025041992)

        fun MapLocation.toPoint(): Point = Point.fromLngLat(longitude, latitude)
    }
}