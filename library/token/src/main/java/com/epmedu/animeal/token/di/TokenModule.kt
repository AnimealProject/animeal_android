package com.epmedu.animeal.token.di

import com.epmedu.animeal.token.errorhandler.TokenExpirationHandler
import com.epmedu.animeal.token.errorhandler.TokenExpirationHandlerImpl
import com.epmedu.animeal.token.refreshtoken.RefreshTokenApi
import com.epmedu.animeal.token.refreshtoken.RefreshTokenApiImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TokenModule {

    @Singleton
    @Provides
    fun providesRefreshTokenApi(): RefreshTokenApi = RefreshTokenApiImpl()

    @Singleton
    @Provides
    fun providesTokenExpirationHandler(
        refreshTokenApi: RefreshTokenApi
    ): TokenExpirationHandler = TokenExpirationHandlerImpl(refreshTokenApi)
}