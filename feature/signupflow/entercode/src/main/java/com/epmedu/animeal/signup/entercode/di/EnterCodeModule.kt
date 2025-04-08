package com.epmedu.animeal.signup.entercode.di

import com.epmedu.animeal.auth.AuthAPI
import com.epmedu.animeal.profile.domain.repository.ProfileRepository
import com.epmedu.animeal.signup.entercode.data.EnterCodeRepositoryImpl
import com.epmedu.animeal.signup.entercode.domain.EnterCodeRepository
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
        profileRepository: ProfileRepository,
        authAPI: AuthAPI,
    ): EnterCodeRepository = EnterCodeRepositoryImpl(profileRepository, authAPI)

    @ViewModelScoped
    @Provides
    fun provideMobileConfirmCodeUseCase(
        repository: EnterCodeRepository
    ) = MobileConfirmCodeUseCase(repository)

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