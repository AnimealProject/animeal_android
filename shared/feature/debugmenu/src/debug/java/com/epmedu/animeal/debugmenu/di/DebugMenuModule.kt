package com.epmedu.animeal.debugmenu.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
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
    fun providesDebugMenuRepository(
        dataStore: DataStore<Preferences>
    ): DebugMenuRepository = DebugMenuRepositoryImpl(dataStore)
}