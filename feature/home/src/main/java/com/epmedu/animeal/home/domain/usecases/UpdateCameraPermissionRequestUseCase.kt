package com.epmedu.animeal.home.domain.usecases

import com.epmedu.animeal.common.domain.ApplicationSettingsRepository

class UpdateCameraPermissionRequestUseCase(
    private val repository: ApplicationSettingsRepository
) {
    suspend operator fun invoke(value: Boolean) {
        repository.updateAppSettings {
            isCameraPermissionRequested = value
        }
    }
}