package com.epmedu.animeal.profile.di

import com.epmedu.animeal.auth.AuthAPI
import com.epmedu.animeal.profile.data.repository.AuthenticationTypeRepository
import com.epmedu.animeal.profile.data.repository.AuthenticationTypeRepositoryImpl
import com.epmedu.animeal.profile.domain.authenticationtype.GetAuthenticationTypeUseCase
import com.epmedu.animeal.profile.domain.authenticationtype.UpdateAuthenticationTypeUseCase
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
    ): AuthenticationTypeRepository = AuthenticationTypeRepositoryImpl(authAPI)

    @ViewModelScoped
    @Provides
    fun provideGetAuthenticationTypeUseCase(
        repository: AuthenticationTypeRepository,
    ) = GetAuthenticationTypeUseCase(repository)

    @ViewModelScoped
    @Provides
    fun provideUpdateAuthenticationTypeUseCase(
        repository: AuthenticationTypeRepository,
    ) = UpdateAuthenticationTypeUseCase(repository)
}