package com.epmedu.animeal.home.presentation.viewmodel.providers

import com.epmedu.animeal.common.component.BuildConfigProvider
import com.epmedu.animeal.geolocation.gpssetting.GpsSettingsProvider
import com.epmedu.animeal.geolocation.location.LocationProvider
import javax.inject.Inject

class HomeProviders @Inject constructor(
    private val buildConfigProvider: BuildConfigProvider,
    private val locationProvider: LocationProvider,
    private val gpsSettingsProvider: GpsSettingsProvider
) : BuildConfigProvider by buildConfigProvider,
    LocationProvider by locationProvider,
    GpsSettingsProvider by gpsSettingsProvider