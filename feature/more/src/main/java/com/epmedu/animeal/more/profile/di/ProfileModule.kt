package com.epmedu.animeal.more.profile.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.epmedu.animeal.common.data.repository.ProfileRepository
import com.epmedu.animeal.common.data.repository.ProfileRepositoryImpl
import com.epmedu.animeal.foundation.common.validation.validator.DefaultProfileValidator
import com.epmedu.animeal.foundation.common.validation.validator.ProfileValidator
import com.epmedu.animeal.more.profile.domain.GetProfileUseCase
import com.epmedu.animeal.more.profile.domain.SaveProfileUseCase
import com.epmedu.animeal.more.profile.domain.ValidateBirthDateUseCase
import com.epmedu.animeal.more.profile.domain.ValidateEmailUseCase
import com.epmedu.animeal.more.profile.domain.ValidateNameUseCase
import com.epmedu.animeal.more.profile.domain.ValidateSurnameUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal object ProfileModule {

    @ViewModelScoped
    @Provides
    fun provideProfileValidator(): ProfileValidator = DefaultProfileValidator()

    @ViewModelScoped
    @Provides
    fun provideProfileRepository(
        dataStore: DataStore<Preferences>
    ): ProfileRepository = ProfileRepositoryImpl(dataStore)

    @ViewModelScoped
    @Provides
    fun provideGetProfileUseCase(
        repository: ProfileRepository
    ) = GetProfileUseCase(repository)

    @ViewModelScoped
    @Provides
    fun provideSaveProfileUseCase(
        repository: ProfileRepository
    ) = SaveProfileUseCase(repository)

    @ViewModelScoped
    @Provides
    fun provideValidateNameUseCase(
        validator: ProfileValidator
    ) = ValidateNameUseCase(validator)

    @ViewModelScoped
    @Provides
    fun provideValidateSurnameUseCase(
        validator: ProfileValidator
    ) = ValidateSurnameUseCase(validator)

    @ViewModelScoped
    @Provides
    fun provideValidateEmailUseCase(
        validator: ProfileValidator
    ) = ValidateEmailUseCase(validator)

    @ViewModelScoped
    @Provides
    fun provideValidateBirthDateUseCase(
        validator: ProfileValidator
    ) = ValidateBirthDateUseCase(validator)
}