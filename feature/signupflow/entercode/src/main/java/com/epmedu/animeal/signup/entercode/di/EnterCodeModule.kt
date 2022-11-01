package com.epmedu.animeal.signup.entercode.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.epmedu.animeal.signup.entercode.data.EnterCodeRepository
import com.epmedu.animeal.signup.entercode.data.EnterCodeRepositoryImpl
import com.epmedu.animeal.signup.entercode.domain.ConfirmCodeUseCase
import com.epmedu.animeal.signup.entercode.domain.SendCodeUseCase
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

    @ViewModelScoped
    @Provides
    fun provideConfirmCodeUseCase(
        repository: EnterCodeRepository
    ) = ConfirmCodeUseCase(repository)

    @ViewModelScoped
    @Provides
    fun provideSendCodeUseCase(
        repository: EnterCodeRepository
    ) = SendCodeUseCase(repository)
}