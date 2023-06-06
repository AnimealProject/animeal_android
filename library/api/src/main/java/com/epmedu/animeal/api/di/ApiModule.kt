package com.epmedu.animeal.api.di

import com.epmedu.animeal.api.AnimealApi
import com.epmedu.animeal.api.AnimealApiImpl
import com.epmedu.animeal.api.donate.DonateApi
import com.epmedu.animeal.api.donate.DonateApiImpl
import com.epmedu.animeal.api.faq.FAQApi
import com.epmedu.animeal.api.faq.FAQApiImpl
import com.epmedu.animeal.api.favourite.FavouriteApi
import com.epmedu.animeal.api.favourite.FavouriteApiImpl
import com.epmedu.animeal.api.feeding.FeedingApi
import com.epmedu.animeal.api.feeding.FeedingApiImpl
import com.epmedu.animeal.api.feeding.FeedingPointApi
import com.epmedu.animeal.api.feeding.FeedingPointApiImpl
import com.epmedu.animeal.api.storage.StorageApi
import com.epmedu.animeal.api.storage.StorageApiImpl
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
    fun providesAnimealApi(): AnimealApi = AnimealApiImpl()

    @Singleton
    @Provides
    fun providesFeedingPointApi(
        animealApi: AnimealApi
    ): FeedingPointApi = FeedingPointApiImpl(animealApi)

    @Singleton
    @Provides
    fun providesFeedingApi(
        animealApi: AnimealApi
    ): FeedingApi = FeedingApiImpl(animealApi)

    @Singleton
    @Provides
    fun providesFavouriteApi(
        animealApi: AnimealApi
    ): FavouriteApi = FavouriteApiImpl(animealApi)

    @Singleton
    @Provides
    fun providesFAQApi(
        animealApi: AnimealApi
    ): FAQApi = FAQApiImpl(animealApi)

    @Singleton
    @Provides
    fun providesStorageApi(): StorageApi = StorageApiImpl()

    @Singleton
    @Provides
    fun providesDonateApi(
        animealApi: AnimealApi
    ): DonateApi = DonateApiImpl(animealApi)
}