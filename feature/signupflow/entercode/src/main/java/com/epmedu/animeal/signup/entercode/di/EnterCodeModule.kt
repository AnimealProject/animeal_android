package com.epmedu.animeal.signup.entercode.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.epmedu.animeal.auth.AuthAPI
import com.epmedu.animeal.signup.entercode.data.EnterCodeRepositoryImpl
import com.epmedu.animeal.signup.entercode.domain.EnterCodeRepository
import com.epmedu.animeal.signup.entercode.domain.FacebookConfirmCodeUseCase
import com.epmedu.animeal.signup.entercode.domain.GetPhoneNumberUseCase
import com.epmedu.animeal.signup.entercode.domain.MobileConfirmCodeUseCase
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
        dataStore: DataStore<Preferences>,
        authAPI: AuthAPI,
    ): EnterCodeRepository = EnterCodeRepositoryImpl(dataStore, authAPI)

    @ViewModelScoped
    @Provides
    fun provideMobileConfirmCodeUseCase(
        repository: EnterCodeRepository
    ) = MobileConfirmCodeUseCase(repository)

    @ViewModelScoped
    @Provides
    fun provideFacebookConfirmCodeUseCase(
        repository: EnterCodeRepository
    ) = FacebookConfirmCodeUseCase(repository)

    @ViewModelScoped
    @Provides
    fun provideSendCodeUseCase(
        repository: EnterCodeRepository
    ) = SendCodeUseCase(repository)

    @ViewModelScoped
    @Provides
    fun provideGetPhoneNumberUseCase(
        repository: EnterCodeRepository
    ) = GetPhoneNumberUseCase(repository)
}