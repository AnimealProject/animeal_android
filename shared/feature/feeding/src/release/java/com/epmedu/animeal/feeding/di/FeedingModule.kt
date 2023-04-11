package com.epmedu.animeal.feeding.di

import com.epmedu.animeal.api.favourite.FavouriteApi
import com.epmedu.animeal.api.feeding.FeedingPointApi
import com.epmedu.animeal.auth.AuthAPI
import com.epmedu.animeal.feeding.data.repository.FavouriteRepositoryImpl
import com.epmedu.animeal.feeding.data.repository.FeedingPointRepositoryImpl
import com.epmedu.animeal.feeding.domain.repository.FavouriteRepository
import com.epmedu.animeal.feeding.domain.repository.FeedingPointRepository
import com.epmedu.animeal.feeding.domain.usecase.AddFeedingPointToFavouritesUseCase
import com.epmedu.animeal.feeding.domain.usecase.RemoveFeedingPointFromFavouritesUseCase
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
        authApi: AuthAPI,
        feedingPointApi: FeedingPointApi,
        favouriteRepository: FavouriteRepository,
    ): FeedingPointRepository = FeedingPointRepositoryImpl(
        dispatchers = Dispatchers,
        authApi = authApi,
        favouriteRepository = favouriteRepository,
        feedingPointApi = feedingPointApi,
    )

    @Singleton
    @Provides
    fun providesFeedingRepository(
        authApi: AuthAPI,
        feedingAPI: FeedingApi,
        favouriteRepository: FavouriteRepository
    ): FeedingRepository {
        return FeedingRepositoryImpl(
            dispatchers = Dispatchers,
            authApi = authApi,
            feedingApi = feedingAPI,
            favouriteRepository = favouriteRepository
        )
    }

    @Singleton
    @Provides
    fun providesFavouriteRepository(
        authApi: AuthAPI,
        favouriteApi: FavouriteApi
    ): FavouriteRepository = FavouriteRepositoryImpl(
        dispatchers = Dispatchers,
        authApi = authApi,
        favouriteApi = favouriteApi
    )

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