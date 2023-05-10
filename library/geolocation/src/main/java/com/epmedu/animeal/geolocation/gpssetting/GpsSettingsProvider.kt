package com.epmedu.animeal.geolocation.gpssetting

import kotlinx.coroutines.flow.Flow

/** Provides info about GPS setting */
interface GpsSettingsProvider {

    val isGpsSettingsEnabled: Boolean

    val isGpsPermissionGranted: Boolean

    fun fetchGpsSettingsUpdates(): Flow<GpsSettingState>

    fun setGpsPermissionGranted(isGranted: Boolean)

    sealed interface GpsSettingState {

        object Enabled : GpsSettingState

        object Disabled : GpsSettingState
    }
}