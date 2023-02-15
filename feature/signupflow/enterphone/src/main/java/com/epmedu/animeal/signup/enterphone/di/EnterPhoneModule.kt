package com.epmedu.animeal.signup.enterphone.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.epmedu.animeal.auth.AuthAPI
import com.epmedu.animeal.signup.enterphone.data.EnterPhoneRepository
import com.epmedu.animeal.signup.enterphone.data.EnterPhoneRepositoryImpl
import com.epmedu.animeal.signup.enterphone.domain.SavePhoneNumberInfoUseCase
import com.epmedu.animeal.signup.enterphone.domain.SignUpAndSignInUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal object EnterPhoneModule {

    @ViewModelScoped
    @Provides
    fun providesEnterPhoneRepository(
        dataStore: DataStore<Preferences>,
        authAPI: AuthAPI,
    ): EnterPhoneRepository = EnterPhoneRepositoryImpl(dataStore, authAPI)

    @ViewModelScoped
    @Provides
    fun provideSignUpAndSignInUseCase(
        repository: EnterPhoneRepository
    ) = SignUpAndSignInUseCase(repository)

    @ViewModelScoped
    @Provides
    fun provideSavePhoneNumberUseCase(
        repository: EnterPhoneRepository
    ) = SavePhoneNumberInfoUseCase(repository)
}