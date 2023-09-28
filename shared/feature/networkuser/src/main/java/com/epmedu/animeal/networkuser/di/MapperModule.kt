package com.epmedu.animeal.networkuser.di

import com.epmedu.animeal.networkuser.data.mapper.AuthUserAttributesToProfileMapper
import com.epmedu.animeal.networkuser.data.mapper.ProfileToAuthUserAttributesMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {

    @Singleton
    @Provides
    fun provideAuthUserAttributesToProfileMapper() =
        AuthUserAttributesToProfileMapper()

    @Singleton
    @Provides
    fun provideProfileToAuthUserMapper() = ProfileToAuthUserAttributesMapper()
}