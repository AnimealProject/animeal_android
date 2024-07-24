package com.epmedu.animeal.geolocation.location

import android.Manifest.permission.ACCESS_FINE_LOCATION
import androidx.annotation.RequiresPermission
import com.epmedu.animeal.geolocation.location.model.Location
import kotlinx.coroutines.flow.Flow

interface LocationProvider {

    @RequiresPermission(ACCESS_FINE_LOCATION)
    fun fetchUpdates(): Flow<Location>
}