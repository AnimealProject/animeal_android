package com.epmedu.animeal.geolocation.gpssetting

import android.Manifest.permission.ACCESS_FINE_LOCATION
import androidx.annotation.RequiresPermission
import kotlinx.coroutines.flow.Flow

/** Provides info about GPS setting. */
interface GpsSettingsProvider {

    val gpsSettingState: GpsSettingState

    @RequiresPermission(ACCESS_FINE_LOCATION)
    fun fetchGpsSettingsUpdates(): Flow<GpsSettingState>
}