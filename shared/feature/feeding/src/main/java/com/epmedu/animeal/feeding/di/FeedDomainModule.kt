package com.epmedu.animeal.feeding.di

import com.epmedu.animeal.feeding.domain.repository.FavouriteRepository
import com.epmedu.animeal.feeding.domain.repository.FeedingRepository
import com.epmedu.animeal.feeding.domain.usecase.AddFeedingPointToFavouritesUseCase
import com.epmedu.animeal.feeding.domain.usecase.GetFeedingHistoriesUseCase
import com.epmedu.animeal.feeding.domain.usecase.GetFeedingInProgressUseCase
import com.epmedu.animeal.feeding.domain.usecase.RemoveFeedingPointFromFavouritesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FeedDomainModule {

    @Singleton
    @Provides
    fun providesGetFeedingInProgressUseCase(
        feedingRepository: FeedingRepository
    ): GetFeedingInProgressUseCase = GetFeedingInProgressUseCase(feedingRepository)

    @Singleton
    @Provides
    fun providesGetFeedingHistoriesUseCase(
        feedingRepository: FeedingRepository
    ): GetFeedingHistoriesUseCase = GetFeedingHistoriesUseCase(feedingRepository)

    @Singleton
    @Provides
    fun providesAddFavouriteFeedingPointUseCase(
        repo: FavouriteRepository
    ) = AddFeedingPointToFavouritesUseCase(repo)

    @Singleton
    @Provides
    fun providesDeleteFavouriteFeedingPointUseCase(
        repo: FavouriteRepository
    ) = RemoveFeedingPointFromFavouritesUseCase(repo)
}