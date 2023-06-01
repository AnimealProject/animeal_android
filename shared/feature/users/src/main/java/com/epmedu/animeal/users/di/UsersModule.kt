package com.epmedu.animeal.users.di

import com.epmedu.animeal.api.AnimealApi
import com.epmedu.animeal.users.data.api.UsersApi
import com.epmedu.animeal.users.data.api.UsersApiImpl
import com.epmedu.animeal.users.data.repository.UsersRepositoryImpl
import com.epmedu.animeal.users.domain.UsersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UsersModule {

    @Singleton
    @Provides
    internal fun providesUsersRepository(
        usersApi: UsersApi
    ): UsersRepository = UsersRepositoryImpl(usersApi)

    @Singleton
    @Provides
    internal fun providesUsersApi(
        animealApi: AnimealApi
    ): UsersApi = UsersApiImpl(animealApi)
}