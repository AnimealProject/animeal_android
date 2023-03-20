package com.epmedu.animeal.feeding.di

import com.epmedu.animeal.api.favourite.FavouriteApi
import com.epmedu.animeal.api.feeding.FeedingPointApi
import com.epmedu.animeal.auth.AuthAPI
import com.epmedu.animeal.debugmenu.domain.DebugMenuRepository
import com.epmedu.animeal.feeding.data.repository.FavouriteRepositoryImpl
import com.epmedu.animeal.feeding.data.repository.FavouriteRepositoryMock
import com.epmedu.animeal.feeding.data.repository.FeedingPointRepositoryImpl
import com.epmedu.animeal.feeding.data.repository.FeedingPointRepositoryMock
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
        favouriteRepository: FavouriteRepository,
        feedingPointApi: FeedingPointApi,
        debugMenuRepository: DebugMenuRepository,
    ): FeedingPointRepository {
        return when {
            debugMenuRepository.useMockedFeedingPoints -> {
                FeedingPointRepositoryMock(
                    dispatchers = Dispatchers,
                    favouriteRepository = favouriteRepository
                )
            }
            else -> {
                FeedingPointRepositoryImpl(
                    authApi = authApi,
                    feedingPointApi = feedingPointApi,
                    favouriteRepository = favouriteRepository,
                    dispatchers = Dispatchers
                )
            }
        }
    }

    @Singleton
    @Provides
    fun providesFavouriteRepository(
        authApi: AuthAPI,
        favouriteApi: FavouriteApi,
        debugMenuRepository: DebugMenuRepository,
    ): FavouriteRepository {
        return when {
            debugMenuRepository.useMockedFeedingPoints -> {
                FavouriteRepositoryMock()
            }
            else -> {
                FavouriteRepositoryImpl(
                    dispatchers = Dispatchers,
                    authApi = authApi,
                    favouriteApi = favouriteApi
                )
            }
        }
    }

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