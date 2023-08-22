package com.epmedu.animeal.auth.di

import com.epmedu.animeal.auth.AuthAPI
import com.epmedu.animeal.auth.AuthAPIImpl
import com.epmedu.animeal.auth.UserAttributesAPI
import com.epmedu.animeal.auth.UserAttributesAPIImpl
import com.epmedu.animeal.token.errorhandler.TokenExpirationHandler
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
    fun providesAuthApi(
        userAttributesAPI: UserAttributesAPI,
        tokenExpirationHandler: TokenExpirationHandler
    ): AuthAPI = AuthAPIImpl(userAttributesAPI, tokenExpirationHandler)

    @Singleton
    @Provides
    fun providesUserAttributesApi(
        tokenExpirationHandler: TokenExpirationHandler
    ): UserAttributesAPI = UserAttributesAPIImpl(tokenExpirationHandler)
}