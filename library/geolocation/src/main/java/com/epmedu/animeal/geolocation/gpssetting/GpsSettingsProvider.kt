package com.epmedu.animeal.geolocation.gpssetting

import kotlinx.coroutines.flow.Flow

/** Provides info about GPS setting */
interface GpsSettingsProvider {

    val isGpsSettingsEnabled: Boolean

    fun fetchUpdates(): Flow<GpsSettingState>

    fun changeGpsSettings()

    sealed interface GpsSettingState {

        object Enabled : GpsSettingState

        object Disabled : GpsSettingState
    }
}