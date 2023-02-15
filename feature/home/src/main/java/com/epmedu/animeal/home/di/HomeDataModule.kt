package com.epmedu.animeal.home.di

import com.epmedu.animeal.common.component.AppSettingsProvider
import com.epmedu.animeal.home.data.ApplicationSettingsRepository
import com.epmedu.animeal.home.data.ApplicationSettingsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object HomeDataModule {

    @ViewModelScoped
    @Provides
    fun providesApplicationSettingsRepository(
        appSettingsProvider: AppSettingsProvider,
    ): ApplicationSettingsRepository = ApplicationSettingsRepositoryImpl(appSettingsProvider)
}