package com.epmedu.animeal.home

import com.epmedu.animeal.common.component.BuildConfigProvider
import com.epmedu.animeal.component.BuildConfigProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppScopeModule {

    @Singleton
    @Provides
    fun providesBuildConfigProvider(): BuildConfigProvider = BuildConfigProviderImpl()
}