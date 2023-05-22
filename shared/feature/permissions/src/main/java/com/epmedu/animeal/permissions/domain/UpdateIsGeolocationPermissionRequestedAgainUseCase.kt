package com.epmedu.animeal.permissions.domain

import com.epmedu.animeal.common.domain.ApplicationSettingsRepository

class UpdateIsGeolocationPermissionRequestedAgainUseCase(
    private val repository: ApplicationSettingsRepository
) {

    suspend operator fun invoke(isAsked: Boolean) {
        repository.updateAppSettings {
            isGeolocationPermissionRequestedAgain = isAsked
        }
    }
}