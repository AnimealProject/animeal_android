package com.epmedu.animeal.profile.di

import com.epmedu.animeal.auth.AuthAPI
import com.epmedu.animeal.profile.data.mapper.ProfileToAuthUserAttributesMapper
import com.epmedu.animeal.profile.data.repository.NetworkRepository
import com.epmedu.animeal.profile.data.repository.NetworkRepositoryImpl
import com.epmedu.animeal.profile.domain.network.DeleteNetworkUserUseCase
import com.epmedu.animeal.profile.domain.network.FetchNetworkUserAttributesUseCase
import com.epmedu.animeal.profile.domain.network.UpdateNetworkProfileUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object NetworkModule {

    @ViewModelScoped
    @Provides
    fun provideNetworkRepository(
        profileToAuthUserMapper: ProfileToAuthUserAttributesMapper,
        authAPI: AuthAPI,
    ): NetworkRepository = NetworkRepositoryImpl(profileToAuthUserMapper, authAPI)

    @ViewModelScoped
    @Provides
    fun provideFetchNetworkUserAttributesUseCase(
        repository: NetworkRepository,
    ) = FetchNetworkUserAttributesUseCase(repository)

    @ViewModelScoped
    @Provides
    fun provideUpdateNetworkProfileUseCase(
        repository: NetworkRepository,
    ) = UpdateNetworkProfileUseCase(repository)

    @ViewModelScoped
    @Provides
    fun provideDeleteNetworkUserUseCase(
        repository: NetworkRepository,
    ) = DeleteNetworkUserUseCase(repository)
}