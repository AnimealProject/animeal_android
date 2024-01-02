package com.epmedu.animeal.networkuser.di

import com.epmedu.animeal.auth.AuthAPI
import com.epmedu.animeal.auth.UserAttributesAPI
import com.epmedu.animeal.networkuser.data.mapper.AuthUserAttributesToProfileMapper
import com.epmedu.animeal.networkuser.data.mapper.ProfileToAuthUserAttributesMapper
import com.epmedu.animeal.networkuser.data.repository.NetworkRepositoryImpl
import com.epmedu.animeal.networkuser.domain.repository.NetworkRepository
import com.epmedu.animeal.users.domain.UsersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkDataModule {

    @Singleton
    @Provides
    fun provideNetworkRepository(
        authAPI: AuthAPI,
        userAttributesAPI: UserAttributesAPI,
        usersRepository: UsersRepository,
        authUserAttributesToProfileMapper: AuthUserAttributesToProfileMapper,
        profileToAuthUserMapper: ProfileToAuthUserAttributesMapper,
    ): NetworkRepository =
        NetworkRepositoryImpl(
            authAPI,
            userAttributesAPI,
            usersRepository,
            authUserAttributesToProfileMapper,
            profileToAuthUserMapper,
        )
}