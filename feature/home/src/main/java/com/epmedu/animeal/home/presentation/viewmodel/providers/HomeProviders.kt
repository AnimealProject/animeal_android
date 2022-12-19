package com.epmedu.animeal.home.presentation.viewmodel.providers

import com.epmedu.animeal.common.component.BuildConfigProvider
import com.epmedu.animeal.feeding.domain.repository.FeedingPointRepository
import com.epmedu.animeal.geolocation.gpssetting.GpsSettingsProvider
import com.epmedu.animeal.geolocation.location.LocationProvider
import com.epmedu.animeal.home.presentation.viewmodel.handlers.gps.GpsHandler
import com.epmedu.animeal.home.presentation.viewmodel.handlers.location.LocationHandler
import javax.inject.Inject

class HomeProviders @Inject constructor(
    private val buildConfigProvider: BuildConfigProvider,
    private val locationProvider: LocationProvider,
    private val gpsSettingsProvider: GpsSettingsProvider,
    private val locationHandler: LocationHandler,
    private val gpsHandler: GpsHandler,
    private val feedingPointRepository: FeedingPointRepository
) : BuildConfigProvider by buildConfigProvider,
    LocationProvider by locationProvider,
    GpsSettingsProvider by gpsSettingsProvider,
    LocationHandler by locationHandler,
    GpsHandler by gpsHandler,
    FeedingPointRepository by feedingPointRepository