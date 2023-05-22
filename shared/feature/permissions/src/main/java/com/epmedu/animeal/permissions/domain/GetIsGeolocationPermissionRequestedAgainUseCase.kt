package com.epmedu.animeal.permissions.domain

import com.epmedu.animeal.common.domain.ApplicationSettingsRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class GetIsGeolocationPermissionRequestedAgainUseCase(
    private val repository: ApplicationSettingsRepository
) {

    operator fun invoke(): Boolean {
        return runBlocking {
            repository.getAppSettings().first().isGeolocationPermissionRequestedAgain
        }
    }
}