package com.epmedu.animeal.favourites.di

import com.epmedu.animeal.favourites.data.FavouritesRepository
import com.epmedu.animeal.favourites.data.FavouritesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal object FavouritesModule {

    @ViewModelScoped
    @Provides
    fun provideFeedSpotRepository(): FavouritesRepository = FavouritesRepositoryImpl()
}