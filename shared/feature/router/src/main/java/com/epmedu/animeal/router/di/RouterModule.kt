package com.epmedu.animeal.router.di

import com.epmedu.animeal.router.data.RouterRepositoryImpl
import com.epmedu.animeal.router.domain.RouterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RouterModule {

    @Singleton
    @Provides
    fun provideRouterRepository(): RouterRepository = RouterRepositoryImpl()
}