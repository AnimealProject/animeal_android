package com.epmedu.animeal.more.profile.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.epmedu.animeal.common.data.repository.ProfileRepository
import com.epmedu.animeal.common.data.repository.ProfileRepositoryImpl
import com.epmedu.animeal.foundation.common.validation.DefaultProfileValidator
import com.epmedu.animeal.foundation.common.validation.ProfileValidator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal object ProfileModule {

    @ViewModelScoped
    @Provides
    fun providesProfileValidator(): ProfileValidator = DefaultProfileValidator()

    @ViewModelScoped
    @Provides
    fun provideProfileRepository(
        dataStore: DataStore<Preferences>
    ): ProfileRepository = ProfileRepositoryImpl(dataStore)
}