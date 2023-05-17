package com.epmedu.animeal.feeding.di

import com.epmedu.animeal.feeding.domain.repository.FavouriteRepository
import com.epmedu.animeal.feeding.domain.usecase.AddFeedingPointToFavouritesUseCase
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
    fun providesAddFavouriteFeedingPointUseCase(
        repo: FavouriteRepository
    ) = AddFeedingPointToFavouritesUseCase(repo)

    @Singleton
    @Provides
    fun providesDeleteFavouriteFeedingPointUseCase(
        repo: FavouriteRepository
    ) = RemoveFeedingPointFromFavouritesUseCase(repo)
}