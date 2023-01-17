package com.epmedu.animeal.networkuser.di

import com.epmedu.animeal.auth.AuthAPI
import com.epmedu.animeal.networkuser.data.repository.AuthenticationTypeRepository
import com.epmedu.animeal.networkuser.data.repository.AuthenticationTypeRepositoryImpl
import com.epmedu.animeal.networkuser.domain.authenticationtype.GetAuthenticationTypeUseCase
import com.epmedu.animeal.networkuser.domain.authenticationtype.SetFacebookAuthenticationTypeUseCase
import com.epmedu.animeal.networkuser.domain.authenticationtype.SetMobileAuthenticationTypeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object AuthenticationTypeModule {

    @ViewModelScoped
    @Provides
    fun provideAuthenticationTypeRepository(
        authAPI: AuthAPI,
    ): AuthenticationTypeRepository =
        AuthenticationTypeRepositoryImpl(authAPI)

    @ViewModelScoped
    @Provides
    fun provideGetAuthenticationTypeUseCase(
        repository: AuthenticationTypeRepository,
    ) = GetAuthenticationTypeUseCase(repository)

    @ViewModelScoped
    @Provides
    fun provideSetMobileAuthenticationTypeUseCase(
        repository: AuthenticationTypeRepository,
    ) = SetMobileAuthenticationTypeUseCase(repository)

    @ViewModelScoped
    @Provides
    fun provideSetFacebookAuthenticationTypeUseCase(
        repository: AuthenticationTypeRepository,
    ) = SetFacebookAuthenticationTypeUseCase(repository)
}