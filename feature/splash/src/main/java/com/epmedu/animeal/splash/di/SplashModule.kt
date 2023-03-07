package com.epmedu.animeal.splash.di

import com.epmedu.animeal.auth.AuthAPI
import com.epmedu.animeal.profile.data.repository.ProfileRepository
import com.epmedu.animeal.router.domain.RouterRepository
import com.epmedu.animeal.splash.data.repository.SplashRepositoryImpl
import com.epmedu.animeal.splash.domain.repository.SplashRepository
import com.epmedu.animeal.splash.domain.usecase.GetIsProfileSavedUseCase
import com.epmedu.animeal.splash.domain.usecase.GetIsSignedInUseCase
import com.epmedu.animeal.splash.domain.usecase.SetFinishProfileAsStartDestinationUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object SplashModule {
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

    @ViewModelScoped
    @Provides
    fun provideGetIsProfileSavedUseCase(
        repository: ProfileRepository
    ) = GetIsProfileSavedUseCase(repository)

    @ViewModelScoped
    @Provides
    fun provideSetFinishProfileAsStartDestinationUseCase(
        repository: RouterRepository
    ) = SetFinishProfileAsStartDestinationUseCase(repository)
}