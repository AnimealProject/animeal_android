package com.epmedu.animeal.home.presentation.viewmodel.handlers.gps

import com.epmedu.animeal.geolocation.gpssetting.GpsSettingsProvider

interface GpsHandler {
    fun collectGpsSettings(state: GpsSettingsProvider.GpsSettingState)
}