package com.epmedu.animeal.feeding.di

import com.epmedu.animeal.api.favourite.FavouriteApi
import com.epmedu.animeal.api.feeding.FeedingPointApi
import com.epmedu.animeal.auth.AuthAPI
import com.epmedu.animeal.feeding.data.repository.FeedingPointRepositoryImpl
import com.epmedu.animeal.feeding.domain.repository.FeedingPointRepository
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
        authAPI: AuthAPI,
        favouriteApi: FavouriteApi,
        feedingPointApi: FeedingPointApi
    ): FeedingPointRepository = FeedingPointRepositoryImpl(
        authAPI = authAPI,
        favouriteApi = favouriteApi,
        feedingPointApi = feedingPointApi,
        dispatchers = Dispatchers
    )
}