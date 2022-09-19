package com.epmedu.animeal.home.di

import com.epmedu.animeal.home.data.FeedingPointRepository
import com.epmedu.animeal.home.data.FeedingPointRepositoryImpl
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
    fun provideFeedingPointRepository(): FeedingPointRepository = FeedingPointRepositoryImpl()
}