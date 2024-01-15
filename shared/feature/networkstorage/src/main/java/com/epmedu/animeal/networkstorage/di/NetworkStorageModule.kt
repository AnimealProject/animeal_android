package com.epmedu.animeal.networkstorage.di

import com.epmedu.animeal.networkstorage.data.api.StorageApi
import com.epmedu.animeal.networkstorage.data.api.StorageApiImpl
import com.epmedu.animeal.token.errorhandler.TokenExpirationHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkStorageModule {

    @Singleton
    @Provides
    fun providesStorageApi(
        tokenExpirationHandler: TokenExpirationHandler
    ): StorageApi = StorageApiImpl(tokenExpirationHandler)
}