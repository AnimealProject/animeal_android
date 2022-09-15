package com.epmedu.animeal.geolocation.di

import android.content.Context
import com.epmedu.animeal.geolocation.gpssetting.GpsSettingsProvider
import com.epmedu.animeal.geolocation.gpssetting.IntentGpsSettingsProvider
import com.epmedu.animeal.geolocation.location.FusedLocationProviderImpl
import com.epmedu.animeal.geolocation.location.LocationProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class GeolocationModule {

    @Singleton
    @Provides
    fun providesLocationProvider(fusedLocationProviderClient: FusedLocationProviderClient): LocationProvider =
        FusedLocationProviderImpl(fusedLocationProviderClient)

    @Singleton
    @Provides
    fun providesFusedLocationProviderClient(@ApplicationContext context: Context): FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    @Singleton
    @Provides
    fun providesGpsSettingsStrategyProvider(@ApplicationContext context: Context): GpsSettingsProvider =
        IntentGpsSettingsProvider(context)
}