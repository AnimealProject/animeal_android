package com.epmedu.animeal.geolocation.location

import com.epmedu.animeal.geolocation.location.model.Location
import kotlinx.coroutines.flow.Flow

interface LocationProvider {
    fun fetchUpdates(): Flow<Location>
}