package com.epmedu.animeal.signup.enterphone.di

import com.epmedu.animeal.auth.AuthAPI
import com.epmedu.animeal.profile.domain.repository.ProfileRepository
import com.epmedu.animeal.signup.enterphone.data.EnterPhoneRepositoryImpl
import com.epmedu.animeal.signup.enterphone.domain.EnterPhoneRepository
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
        authAPI: AuthAPI,
    ): EnterPhoneRepository = EnterPhoneRepositoryImpl(authAPI)

    @ViewModelScoped
    @Provides
    fun provideSignUpAndSignInUseCase(
        repository: EnterPhoneRepository
    ) = SignUpAndSignInUseCase(repository)

    @ViewModelScoped
    @Provides
    fun provideSavePhoneNumberUseCase(
        repository: ProfileRepository
    ) = SavePhoneNumberInfoUseCase(repository)
}