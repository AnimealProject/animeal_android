package com.epmedu.animeal.auth.di

import com.epmedu.animeal.auth.AuthAPI
import com.epmedu.animeal.auth.UserAttributesAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Singleton
    @Provides
    fun providesAuthApi() = AuthAPI()

    @Singleton
    @Provides
    fun providesUserAttributesApi() = UserAttributesAPI()
}