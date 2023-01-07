package com.epmedu.animeal.feeding.data.model

import com.amplifyframework.datastore.generated.model.Location

data class LocationData(
    val lat: Double? = null,
    val lon: Double? = null
) {
    companion object {
        fun Location.toLocationData() = LocationData(lat, lon)
    }
}
