package com.epmedu.animeal.di

import android.content.Context
import com.epmedu.animeal.common.component.BuildConfigProvider
import com.epmedu.animeal.common.component.GpsSettingsProvider
import com.epmedu.animeal.common.component.LocationProvider
import com.epmedu.animeal.component.BuildConfigProviderImpl
import com.epmedu.animeal.component.IntentGpsSettingsProvider
import com.epmedu.animeal.component.LocationProviderImpl
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
class AppScopeModule {

    @Singleton
    @Provides
    fun providesBuildConfigProvider(): BuildConfigProvider = BuildConfigProviderImpl()

    @Singleton
    @Provides
    fun providesLocationProvider(fusedLocationProviderClient: FusedLocationProviderClient): LocationProvider =
        LocationProviderImpl(fusedLocationProviderClient)

    @Singleton
    @Provides
    fun providesFusedLocationProviderClient(@ApplicationContext context: Context): FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    @Singleton
    @Provides
    fun providesGpsSettingsStrategyProvider(@ApplicationContext context: Context): GpsSettingsProvider =
        IntentGpsSettingsProvider(context)
}