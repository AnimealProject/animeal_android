package com.epmedu.animeal.feedings.di

import com.epmedu.animeal.feeding.domain.repository.FeedingRepository
import com.epmedu.animeal.feedings.domain.usecase.ApproveFeedingUseCase
import com.epmedu.animeal.feedings.domain.usecase.GetAllFeedingsUseCase
import com.epmedu.animeal.feedings.domain.usecase.GetHasReviewedFeedingsUseCase
import com.epmedu.animeal.feedings.domain.usecase.GetIsNewFeedingPendingUseCase
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
    fun providesGetIsNewFeedingPendingUseCase() = GetIsNewFeedingPendingUseCase()

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
}