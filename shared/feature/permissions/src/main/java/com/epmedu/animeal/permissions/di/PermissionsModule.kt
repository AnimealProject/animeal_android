package com.epmedu.animeal.permissions.di

import com.epmedu.animeal.common.domain.ApplicationSettingsRepository
import com.epmedu.animeal.permissions.domain.GetCameraPermissionRequestedUseCase
import com.epmedu.animeal.permissions.domain.GetGeolocationPermissionRequestedUseCase
import com.epmedu.animeal.permissions.domain.UpdateCameraPermissionRequestUseCase
import com.epmedu.animeal.permissions.domain.UpdateGeolocationPermissionRequestedUseCase
import com.epmedu.animeal.permissions.presentation.handler.PermissionsHandler
import com.epmedu.animeal.permissions.presentation.handler.PermissionsHandlerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PermissionsModule {

    @Singleton
    @Provides
    fun providesGetGeolocationPermissionRequestedUseCase(
        applicationSettingsRepository: ApplicationSettingsRepository,
    ): GetGeolocationPermissionRequestedUseCase =
        GetGeolocationPermissionRequestedUseCase(applicationSettingsRepository)

    @Singleton
    @Provides
    fun providesUpdateGeolocationPermissionRequestedUseCase(
        applicationSettingsRepository: ApplicationSettingsRepository,
    ): UpdateGeolocationPermissionRequestedUseCase =
        UpdateGeolocationPermissionRequestedUseCase(applicationSettingsRepository)

    @Singleton
    @Provides
    fun provideGetCameraPermissionRequestedUseCase(
        applicationSettingsRepository: ApplicationSettingsRepository
    ): GetCameraPermissionRequestedUseCase =
        GetCameraPermissionRequestedUseCase(applicationSettingsRepository)

    @Singleton
    @Provides
    fun provideUpdateCameraPermissionRequestUseCase(
        applicationSettingsRepository: ApplicationSettingsRepository
    ): UpdateCameraPermissionRequestUseCase =
        UpdateCameraPermissionRequestUseCase(applicationSettingsRepository)

    @Singleton
    @Provides
    fun providesPermissionsHandler(
        getGeolocationPermissionRequestedUseCase: GetGeolocationPermissionRequestedUseCase,
        getCameraPermissionRequestedUseCase: GetCameraPermissionRequestedUseCase,
        updateGeolocationPermissionRequestedUseCase: UpdateGeolocationPermissionRequestedUseCase,
        updateCameraPermissionRequestUseCase: UpdateCameraPermissionRequestUseCase
    ): PermissionsHandler = PermissionsHandlerImpl(
        getGeolocationPermissionRequestedUseCase,
        updateGeolocationPermissionRequestedUseCase,
        getCameraPermissionRequestedUseCase,
        updateCameraPermissionRequestUseCase
    )
}