package com.epmedu.animeal.api.di

import com.epmedu.animeal.api.faq.FAQApi
import com.epmedu.animeal.api.faq.FAQApiImpl
import com.epmedu.animeal.api.favourite.FavouriteApi
import com.epmedu.animeal.api.favourite.FavouriteApiImpl
import com.epmedu.animeal.api.feeding.FeedingPointApi
import com.epmedu.animeal.api.feeding.FeedingPointApiImpl
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

    @Singleton
    @Provides
    fun providesFAQApi(): FAQApi = FAQApiImpl()
}