package com.epmedu.animeal.camera.domain.usecase

import android.net.Uri
import com.epmedu.animeal.camera.domain.repository.CameraRepository

class UploadPhotoUseCase(private val cameraRepository: CameraRepository) {

    suspend operator fun invoke(fileName: String, uri: Uri) = cameraRepository.uploadPhoto(fileName, uri)
}