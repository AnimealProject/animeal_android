package com.epmedu.animeal.networkuser.di

import com.epmedu.animeal.auth.UserAttributesAPI
import com.epmedu.animeal.networkuser.data.mapper.ProfileToAuthUserAttributesMapper
import com.epmedu.animeal.networkuser.data.repository.NetworkRepositoryImpl
import com.epmedu.animeal.networkuser.domain.repository.NetworkRepository
import com.epmedu.animeal.networkuser.domain.usecase.DeleteNetworkUserUseCase
import com.epmedu.animeal.networkuser.domain.usecase.FetchNetworkUserAttributesUseCase
import com.epmedu.animeal.networkuser.domain.usecase.UpdateNetworkProfileUseCase
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
        userAttributesAPI: UserAttributesAPI,
    ): NetworkRepository =
        NetworkRepositoryImpl(
            profileToAuthUserMapper,
            userAttributesAPI
        )

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