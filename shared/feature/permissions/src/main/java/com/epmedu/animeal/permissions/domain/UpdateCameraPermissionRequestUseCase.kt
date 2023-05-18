package com.epmedu.animeal.permissions.domain

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