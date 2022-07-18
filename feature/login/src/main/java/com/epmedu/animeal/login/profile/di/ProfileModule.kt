package com.epmedu.animeal.login.profile.di

import com.epmedu.animeal.login.profile.data.repository.ProfileRepository
import com.epmedu.animeal.login.profile.data.repository.ProfileRepositoryImpl
import com.epmedu.animeal.login.profile.data.storage.ProfileStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@[Module InstallIn(ViewModelComponent::class)]
internal object ProfileModule {

    @[ViewModelScoped Provides]
    fun provideProfileRepository(storage: ProfileStorage): ProfileRepository =
        ProfileRepositoryImpl(storage)
}