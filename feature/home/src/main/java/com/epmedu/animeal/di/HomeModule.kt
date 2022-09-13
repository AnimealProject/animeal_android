package com.epmedu.animeal.di

import com.epmedu.animeal.common.data.repository.FeedingPointRepository
import com.epmedu.animeal.common.data.repository.FeedingPointRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal object HomeModule {

    @ViewModelScoped
    @Provides
    fun providesFeedingPointRepository(): FeedingPointRepository = FeedingPointRepositoryImpl()
}