package com.epmedu.animeal.feeding.di

import com.epmedu.animeal.api.favourite.FavouriteApi
import com.epmedu.animeal.api.feeding.FeedingActionApi
import com.epmedu.animeal.api.feeding.FeedingApi
import com.epmedu.animeal.api.feeding.FeedingHistoryApi
import com.epmedu.animeal.api.feeding.FeedingPointApi
import com.epmedu.animeal.networkstorage.data.api.StorageApi
import com.epmedu.animeal.auth.AuthAPI
import com.epmedu.animeal.feeding.data.repository.FavouriteRepositoryImpl
import com.epmedu.animeal.feeding.data.repository.FeedingPointRepositoryImpl
import com.epmedu.animeal.feeding.data.repository.FeedingRepositoryImpl
import com.epmedu.animeal.feeding.domain.repository.FavouriteRepository
import com.epmedu.animeal.feeding.domain.repository.FeedingPointRepository
import com.epmedu.animeal.feeding.domain.repository.FeedingRepository
import com.epmedu.animeal.users.domain.UsersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object FeedDataModule {

    @Singleton
    @Provides
    fun providesFeedingPointRepository(
        feedingPointApi: FeedingPointApi,
        favouriteRepository: FavouriteRepository,
        storageApi: StorageApi,
    ): FeedingPointRepository = FeedingPointRepositoryImpl(
        dispatchers = Dispatchers,
        favouriteRepository = favouriteRepository,
        feedingPointApi = feedingPointApi,
        storageApi = storageApi,
    )

    @Singleton
    @Provides
    fun providesFeedingRepository(
        authApi: AuthAPI,
        feedingAPI: FeedingApi,
        feedingActionApi: FeedingActionApi,
        feedingHistoryApi: FeedingHistoryApi,
        feedingPointApi: FeedingPointApi,
        storageApi: StorageApi,
        favouriteRepository: FavouriteRepository,
        usersRepository: UsersRepository
    ): FeedingRepository {
        return FeedingRepositoryImpl(
            dispatchers = Dispatchers,
            authApi = authApi,
            feedingApi = feedingAPI,
            feedingActionApi = feedingActionApi,
            feedingHistoryApi = feedingHistoryApi,
            feedingPointApi = feedingPointApi,
            storageApi = storageApi,
            favouriteRepository = favouriteRepository,
            usersRepository = usersRepository
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
}