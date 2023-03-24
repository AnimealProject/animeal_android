package com.epmedu.animeal.debugmenu.di

import com.epmedu.animeal.debugmenu.data.DebugMenuRepositoryImpl
import com.epmedu.animeal.debugmenu.domain.DebugMenuRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DebugMenuModule {

    @Singleton
    @Provides
    fun providesDebugMenuRepository(): DebugMenuRepository = DebugMenuRepositoryImpl()
}