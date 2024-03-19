package com.epmedu.animeal.permissions.di

import com.epmedu.animeal.common.domain.ApplicationSettingsRepository
import com.epmedu.animeal.permissions.domain.GetAppSettingsUseCase
import com.epmedu.animeal.permissions.domain.UpdateAppSettingsUseCase
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
    fun providesGetAppSettingsUseCase(
        repository: ApplicationSettingsRepository,
    ): GetAppSettingsUseCase = GetAppSettingsUseCase(repository)

    @Singleton
    @Provides
    fun providesUpdateAppSettingsUseCase(
        repository: ApplicationSettingsRepository,
    ): UpdateAppSettingsUseCase = UpdateAppSettingsUseCase(repository)

    @Singleton
    @Provides
    fun providesPermissionsHandler(
        getAppSettingsUseCase: GetAppSettingsUseCase,
        updateAppSettingsUseCase: UpdateAppSettingsUseCase
    ): PermissionsHandler = PermissionsHandlerImpl(
        getAppSettingsUseCase,
        updateAppSettingsUseCase
    )
}