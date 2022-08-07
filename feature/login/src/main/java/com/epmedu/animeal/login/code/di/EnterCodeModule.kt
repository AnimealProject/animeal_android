package com.epmedu.animeal.login.code.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.epmedu.animeal.login.code.data.EnterCodeRepository
import com.epmedu.animeal.login.code.data.EnterCodeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal object EnterCodeModule {

    @ViewModelScoped
    @Provides
    fun providesEnterCodeRepository(
        dataStore: DataStore<Preferences>
    ): EnterCodeRepository = EnterCodeRepositoryImpl(dataStore)
}