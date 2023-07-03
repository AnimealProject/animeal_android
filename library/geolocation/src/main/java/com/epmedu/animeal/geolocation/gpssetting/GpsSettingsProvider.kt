package com.epmedu.animeal.geolocation.gpssetting

import kotlinx.coroutines.flow.Flow

/** Provides info about GPS setting. */
interface GpsSettingsProvider {

    val gpsSettingState: GpsSettingState

    fun fetchGpsSettingsUpdates(): Flow<GpsSettingState>
}