package com.epmedu.animeal.home.di

import androidx.lifecycle.SavedStateHandle
import com.epmedu.animeal.common.domain.ApplicationSettingsRepository
import com.epmedu.animeal.common.domain.usecase.ForcedArgumentsUseCase
import com.epmedu.animeal.feeding.domain.repository.FeedingRepository
import com.epmedu.animeal.feeding.domain.usecase.CancelFeedingUseCase
import com.epmedu.animeal.feeding.domain.usecase.FetchCurrentFeedingPointUseCase
import com.epmedu.animeal.feeding.domain.usecase.FinishFeedingUseCase
import com.epmedu.animeal.feeding.domain.usecase.GetAnimalTypeFromSettingsUseCase
import com.epmedu.animeal.feeding.domain.usecase.RejectFeedingUseCase
import com.epmedu.animeal.feeding.domain.usecase.StartFeedingUseCase
import com.epmedu.animeal.feeding.domain.usecase.UpdateAnimalTypeSettingsUseCase
import com.epmedu.animeal.home.domain.usecases.AnimalTypeUseCase
import com.epmedu.animeal.timer.domain.usecase.GetTimerStateUseCase
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
    fun providesUpdateAnimalTypeSettingsUseCase(
        applicationSettingsRepository: ApplicationSettingsRepository,
    ): UpdateAnimalTypeSettingsUseCase =
        UpdateAnimalTypeSettingsUseCase(applicationSettingsRepository)

    @ViewModelScoped
    @Provides
    fun providesFetchCurrentFeedingUseCase(
        getTimerStateUseCase: GetTimerStateUseCase,
        startTimerUseCase: StartTimerUseCase,
        feedingRepository: FeedingRepository
    ): FetchCurrentFeedingPointUseCase = FetchCurrentFeedingPointUseCase(
        getTimerStateUseCase,
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
    fun providesGetAnimalTypeSettingsUseCase(
        getAnimalTypeFromSettingsUseCase: GetAnimalTypeFromSettingsUseCase,
        forcedFeedingPoint: ForcedArgumentsUseCase
    ): AnimalTypeUseCase = AnimalTypeUseCase(
        getAnimalTypeFromSettingsUseCase,
        forcedFeedingPoint
    )

    @ViewModelScoped
    @Provides
    fun provideForcedFeedingPoint(
        savedStateHandle: SavedStateHandle
    ): ForcedArgumentsUseCase = ForcedArgumentsUseCase(savedStateHandle)
}