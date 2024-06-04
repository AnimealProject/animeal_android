package com.epmedu.animeal.feedings.di

import com.epmedu.animeal.common.domain.ApplicationSettingsRepository
import com.epmedu.animeal.feeding.domain.repository.FeedingRepository
import com.epmedu.animeal.feedings.domain.usecase.ApproveFeedingUseCase
import com.epmedu.animeal.feedings.domain.usecase.GetAllFeedingsUseCase
import com.epmedu.animeal.feedings.domain.usecase.GetHasNewPendingFeedingUseCase
import com.epmedu.animeal.feedings.domain.usecase.GetHasReviewedFeedingsUseCase
import com.epmedu.animeal.feedings.domain.usecase.GetViewedFeedingsUseCase
import com.epmedu.animeal.feedings.domain.usecase.UpdateViewedFeedingsUseCase
import com.epmedu.animeal.networkuser.domain.repository.NetworkRepository
import com.epmedu.animeal.networkuser.domain.usecase.GetCurrentUserGroupUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object FeedingsDomainModule {

    @ViewModelScoped
    @Provides
    fun providesGetHasNewPendingFeedingUseCase(
        getAllFeedingsUseCase: GetAllFeedingsUseCase,
        applicationSettingsRepository: ApplicationSettingsRepository
    ) = GetHasNewPendingFeedingUseCase(getAllFeedingsUseCase, applicationSettingsRepository)

    @ViewModelScoped
    @Provides
    fun provideGetAllFeedingsUseCase(
        getCurrentUserGroupUseCase: GetCurrentUserGroupUseCase,
        feedingRepository: FeedingRepository
    ): GetAllFeedingsUseCase = GetAllFeedingsUseCase(getCurrentUserGroupUseCase, feedingRepository)

    @ViewModelScoped
    @Provides
    fun providesGetHasReviewedFeedingsUseCase(
        feedingRepository: FeedingRepository,
        networkRepository: NetworkRepository
    ) = GetHasReviewedFeedingsUseCase(feedingRepository, networkRepository)

    @ViewModelScoped
    @Provides
    fun provideApproveFeedingUseCase(
        feedingRepository: FeedingRepository
    ): ApproveFeedingUseCase = ApproveFeedingUseCase(feedingRepository)

    @ViewModelScoped
    @Provides
    fun provideGetViewedFeedingsUseCase(
        applicationSettingsRepository: ApplicationSettingsRepository
    ) = GetViewedFeedingsUseCase(applicationSettingsRepository)

    @ViewModelScoped
    @Provides
    fun provideUpdateViewedFeedingsUseCase(
        applicationSettingsRepository: ApplicationSettingsRepository
    ) = UpdateViewedFeedingsUseCase(applicationSettingsRepository)
}