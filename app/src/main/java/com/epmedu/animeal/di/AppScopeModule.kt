package com.epmedu.animeal.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.epmedu.animeal.common.component.AppSettingsProvider
import com.epmedu.animeal.common.component.BuildConfigProvider
import com.epmedu.animeal.component.AppSettingsProviderImpl
import com.epmedu.animeal.component.BuildConfigProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun providesAppSettingsProvider(
        dataStore: DataStore<Preferences>,
    ): AppSettingsProvider = AppSettingsProviderImpl(dataStore)
}