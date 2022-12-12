package com.epmedu.animeal.home.di

import com.epmedu.animeal.common.component.AppSettingsProvider
import com.epmedu.animeal.feeding.data.repository.FeedingPointRepositoryImpl
import com.epmedu.animeal.feeding.domain.repository.FeedingPointRepository
import com.epmedu.animeal.home.data.ApplicationSettingsRepository
import com.epmedu.animeal.home.data.ApplicationSettingsRepositoryImpl
import com.epmedu.animeal.home.domain.GetGeolocationPermissionRequestedSettingUseCase
import com.epmedu.animeal.home.domain.UpdateGeolocationPermissionRequestedSettingUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal object HomeModule {

    @ViewModelScoped
    @Provides
    fun providesFeedingPointRepository(): FeedingPointRepository = FeedingPointRepositoryImpl()

    @ViewModelScoped
    @Provides
    fun providesApplicationSettingsRepository(
        appSettingsProvider: AppSettingsProvider,
    ): ApplicationSettingsRepository = ApplicationSettingsRepositoryImpl(appSettingsProvider)

    @ViewModelScoped
    @Provides
    fun providesGetGeolocationPermissionRequestedSettingUseCase(
        applicationSettingsRepository: ApplicationSettingsRepository,
    ): GetGeolocationPermissionRequestedSettingUseCase =
        GetGeolocationPermissionRequestedSettingUseCase(applicationSettingsRepository)

    @ViewModelScoped
    @Provides
    fun providesUpdateGeolocationPermissionRequestedSettingUseCase(
        applicationSettingsRepository: ApplicationSettingsRepository,
    ): UpdateGeolocationPermissionRequestedSettingUseCase =
        UpdateGeolocationPermissionRequestedSettingUseCase(applicationSettingsRepository)
}