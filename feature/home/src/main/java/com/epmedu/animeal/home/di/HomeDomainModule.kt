package com.epmedu.animeal.home.di

import com.epmedu.animeal.feeding.domain.repository.FeedingPointRepository
import com.epmedu.animeal.home.domain.ApplicationSettingsRepository
import com.epmedu.animeal.home.domain.usecases.CancelFeedingUseCase
import com.epmedu.animeal.home.domain.usecases.FinishFeedingUseCase
import com.epmedu.animeal.home.domain.usecases.GetAllFeedingPointsUseCase
import com.epmedu.animeal.home.domain.usecases.GetGeolocationPermissionRequestedSettingUseCase
import com.epmedu.animeal.home.domain.usecases.RejectFeedingUseCase
import com.epmedu.animeal.home.domain.usecases.StartFeedingUseCase
import com.epmedu.animeal.home.domain.usecases.UpdateGeolocationPermissionRequestedSettingUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object HomeDomainModule {

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

    @ViewModelScoped
    @Provides
    fun providesGetAllFeedingPointsUseCase(
        feedingPointRepository: FeedingPointRepository
    ): GetAllFeedingPointsUseCase = GetAllFeedingPointsUseCase(feedingPointRepository)

    @ViewModelScoped
    @Provides
    fun providesStartFeedingUseCase(
        feedingPointRepository: FeedingPointRepository
    ): StartFeedingUseCase = StartFeedingUseCase(feedingPointRepository)

    @ViewModelScoped
    @Provides
    fun providesCancelFeedingUseCase(
        feedingPointRepository: FeedingPointRepository
    ): CancelFeedingUseCase = CancelFeedingUseCase(feedingPointRepository)

    @ViewModelScoped
    @Provides
    fun providesRejectFeedingUseCase(
        feedingPointRepository: FeedingPointRepository
    ): RejectFeedingUseCase = RejectFeedingUseCase(feedingPointRepository)

    @ViewModelScoped
    @Provides
    fun providesFinishFeedingUseCase(
        feedingPointRepository: FeedingPointRepository
    ): FinishFeedingUseCase = FinishFeedingUseCase(feedingPointRepository)
}