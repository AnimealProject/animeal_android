package com.epmedu.animeal.home.di

import androidx.lifecycle.SavedStateHandle
import com.epmedu.animeal.common.domain.ApplicationSettingsRepository
import com.epmedu.animeal.common.domain.usecase.ForcedArgumentsUseCase
import com.epmedu.animeal.feeding.domain.repository.FeedingPointRepository
import com.epmedu.animeal.feeding.domain.repository.FeedingRepository
import com.epmedu.animeal.feeding.domain.usecase.CancelFeedingUseCase
import com.epmedu.animeal.feeding.domain.usecase.FetchCurrentFeedingPointUseCase
import com.epmedu.animeal.feeding.domain.usecase.FinishFeedingUseCase
import com.epmedu.animeal.feeding.domain.usecase.GetAllFeedingPointsUseCase
import com.epmedu.animeal.feeding.domain.usecase.RejectFeedingUseCase
import com.epmedu.animeal.feeding.domain.usecase.StartFeedingUseCase
import com.epmedu.animeal.feeding.domain.usecase.UpdateAnimalTypeSettingsUseCase
import com.epmedu.animeal.home.domain.usecases.AnimalTypeUseCase
import com.epmedu.animeal.home.domain.usecases.GetCameraPermissionRequestedUseCase
import com.epmedu.animeal.home.domain.usecases.GetGeolocationPermissionRequestedSettingUseCase
import com.epmedu.animeal.home.domain.usecases.UpdateCameraPermissionRequestUseCase
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
    fun providesUpdateAnimalTypeSettingsUseCase(
        applicationSettingsRepository: ApplicationSettingsRepository,
    ): UpdateAnimalTypeSettingsUseCase =
        UpdateAnimalTypeSettingsUseCase(applicationSettingsRepository)

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

    @ViewModelScoped
    @Provides
    fun provideGetCameraPermissionRequestedUseCase(
        applicationSettingsRepository: ApplicationSettingsRepository
    ): GetCameraPermissionRequestedUseCase =
        GetCameraPermissionRequestedUseCase(applicationSettingsRepository)

    @ViewModelScoped
    @Provides
    fun provideUpdateCameraPermissionRequestUseCase(
        applicationSettingsRepository: ApplicationSettingsRepository
    ): UpdateCameraPermissionRequestUseCase =
        UpdateCameraPermissionRequestUseCase(applicationSettingsRepository)

    @ViewModelScoped
    @Provides
    fun providesGetAnimalTypeSettingsUseCase(
        repository: ApplicationSettingsRepository,
        forcedFeedingPoint: ForcedArgumentsUseCase
    ): AnimalTypeUseCase = AnimalTypeUseCase(repository, forcedFeedingPoint)

    @ViewModelScoped
    @Provides
    fun provideForcedFeedingPoint(
        savedStateHandle: SavedStateHandle
    ): ForcedArgumentsUseCase = ForcedArgumentsUseCase(savedStateHandle)
}