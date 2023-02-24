package com.epmedu.animeal.splash.di

import com.epmedu.animeal.auth.AuthAPI
import com.epmedu.animeal.splash.data.repository.SplashRepositoryImpl
import com.epmedu.animeal.splash.domain.repository.SplashRepository
import com.epmedu.animeal.splash.domain.usecase.GetIsSignedInUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class SplashModule {
    @ViewModelScoped
    @Provides
    fun providesSplashRepository(
        authAPI: AuthAPI,
    ): SplashRepository = SplashRepositoryImpl(authAPI)

    @ViewModelScoped
    @Provides
    fun provideGetIsSignedInUseCase(
        repository: SplashRepository
    ) = GetIsSignedInUseCase(repository)
}