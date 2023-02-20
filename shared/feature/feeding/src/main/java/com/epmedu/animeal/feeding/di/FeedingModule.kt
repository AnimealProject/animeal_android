package com.epmedu.animeal.feeding.di

import com.epmedu.animeal.api.favourite.FavouriteApi
import com.epmedu.animeal.api.feeding.FeedingPointApi
import com.epmedu.animeal.auth.AuthAPI
import com.epmedu.animeal.feeding.data.repository.FavouriteRepositoryImpl
import com.epmedu.animeal.feeding.data.repository.FeedingPointRepositoryImpl
import com.epmedu.animeal.feeding.domain.repository.FavouriteRepository
import com.epmedu.animeal.feeding.domain.repository.FeedingPointRepository
import com.epmedu.animeal.feeding.domain.usecase.AddFavouriteFeedingPointUseCase
import com.epmedu.animeal.feeding.domain.usecase.DeleteFavouriteFeedingPointUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FeedingModule {

    @Singleton
    @Provides
    fun providesFeedingPointRepository(
        favouriteRepository: FavouriteRepository,
        feedingPointApi: FeedingPointApi
    ): FeedingPointRepository = FeedingPointRepositoryImpl(
        feedingPointApi = feedingPointApi,
        favouriteRepository = favouriteRepository,
        dispatchers = Dispatchers
    )

    @Singleton
    @Provides
    fun providesFavouriteRepository(
        authApi: AuthAPI,
        favouriteApi: FavouriteApi
    ): FavouriteRepository = FavouriteRepositoryImpl(
        authApi,
        favouriteApi
    )

    @Singleton
    @Provides
    fun providesAddFavouriteFeedingPointUseCase(
        repo: FavouriteRepository
    ) = AddFavouriteFeedingPointUseCase(repo)

    @Singleton
    @Provides
    fun providesDeleteFavouriteFeedingPointUseCase(
        repo: FavouriteRepository
    ) = DeleteFavouriteFeedingPointUseCase(repo)
}