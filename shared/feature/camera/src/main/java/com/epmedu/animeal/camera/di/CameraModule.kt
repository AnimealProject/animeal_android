package com.epmedu.animeal.camera.di

import com.epmedu.animeal.camera.data.repository.CameraRepositoryImpl
import com.epmedu.animeal.camera.domain.repository.CameraRepository
import com.epmedu.animeal.camera.domain.usecase.DeletePhotoUseCase
import com.epmedu.animeal.camera.domain.usecase.UploadPhotoUseCase
import com.epmedu.animeal.networkstorage.data.api.StorageApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object CameraModule {

    @ViewModelScoped
    @Provides
    fun provideCameraRepository(storageApi: StorageApi): CameraRepository = CameraRepositoryImpl(storageApi)

    @ViewModelScoped
    @Provides
    fun provideUploadPhotoUseCase(repository: CameraRepository): UploadPhotoUseCase = UploadPhotoUseCase(repository)

    @ViewModelScoped
    @Provides
    fun provideDeletePhotoUseCase(repository: CameraRepository) = DeletePhotoUseCase(repository)
}