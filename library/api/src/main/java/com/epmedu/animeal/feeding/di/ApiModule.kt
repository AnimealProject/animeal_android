package com.epmedu.animeal.feeding.di

import com.epmedu.animeal.feeding.data.api.favourite.FavouriteApi
import com.epmedu.animeal.feeding.data.api.favourite.FavouriteApiImpl
import com.epmedu.animeal.feeding.data.api.feeding.FeedingPointApi
import com.epmedu.animeal.feeding.data.api.feeding.FeedingPointApiImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ApiModule {

    @Singleton
    @Provides
    fun providesFeedingPointApi(): FeedingPointApi = FeedingPointApiImpl()

    @Singleton
    @Provides
    fun providesFavouriteApi(): FavouriteApi = FavouriteApiImpl()
}