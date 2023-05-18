package com.epmedu.animeal.home.di

import com.epmedu.animeal.common.component.AppSettingsProvider
import com.epmedu.animeal.common.domain.ApplicationSettingsRepository
import com.epmedu.animeal.common.domain.ApplicationSettingsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeDataModule {

    @Singleton
    @Provides
    fun providesApplicationSettingsRepository(
        appSettingsProvider: AppSettingsProvider,
    ): ApplicationSettingsRepository =
        ApplicationSettingsRepositoryImpl(appSettingsProvider)
}