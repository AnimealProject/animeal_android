package com.epmedu.animeal.home.di

import com.epmedu.animeal.feeding.domain.repository.FeedingPointRepository
import com.epmedu.animeal.home.domain.ApplicationSettingsRepository
import com.epmedu.animeal.feeding.domain.repository.FeedingRepository
import com.epmedu.animeal.home.domain.usecases.CancelFeedingUseCase
import com.epmedu.animeal.home.domain.usecases.FetchCurrentFeedingPointUseCase
import com.epmedu.animeal.home.domain.usecases.FinishFeedingUseCase
import com.epmedu.animeal.home.domain.usecases.GetAllFeedingPointsUseCase
import com.epmedu.animeal.home.domain.usecases.GetGeolocationPermissionRequestedSettingUseCase
import com.epmedu.animeal.home.domain.usecases.RejectFeedingUseCase
import com.epmedu.animeal.home.domain.usecases.StartFeedingUseCase
import com.epmedu.animeal.home.domain.usecases.UpdateGeolocationPermissionRequestedSettingUseCase
import com.epmedu.animeal.timer.domain.usecase.StartTimerUseCase
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
    fun providesFetchCurrentFeedingUseCase(
        startTimerUseCase: StartTimerUseCase,
        feedingRepository: FeedingRepository
    ): FetchCurrentFeedingPointUseCase = FetchCurrentFeedingPointUseCase(
        startTimerUseCase,
        feedingRepository
    )

    @ViewModelScoped
    @Provides
    fun providesStartFeedingUseCase(
        feedingRepository: FeedingRepository
    ): StartFeedingUseCase = StartFeedingUseCase(feedingRepository)

    @ViewModelScoped
    @Provides
    fun providesCancelFeedingUseCase(
        feedingRepository: FeedingRepository
    ): CancelFeedingUseCase = CancelFeedingUseCase(feedingRepository)

    @ViewModelScoped
    @Provides
    fun providesRejectFeedingUseCase(
        feedingRepository: FeedingRepository
    ): RejectFeedingUseCase = RejectFeedingUseCase(feedingRepository)

    @ViewModelScoped
    @Provides
    fun providesFinishFeedingUseCase(
        feedingRepository: FeedingRepository
    ): FinishFeedingUseCase = FinishFeedingUseCase(feedingRepository)
}