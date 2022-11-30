package com.epmedu.animeal.home.di

import com.epmedu.animeal.feeding.data.repository.FeedingPointRepositoryImpl
import com.epmedu.animeal.feeding.domain.repository.FeedingPointRepository
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