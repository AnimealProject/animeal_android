package com.epmedu.animeal.home.domain.usecases

import com.epmedu.animeal.home.data.ApplicationSettingsRepository

class UpdateGeolocationPermissionRequestedSettingUseCase(
    private val repository: ApplicationSettingsRepository,
) {
    suspend operator fun invoke(value: Boolean) {
        repository.updateAppSettings {
            isInitialGeolocationPermissionRequested = value
        }
    }
}