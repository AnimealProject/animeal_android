package com.epmedu.animeal.permissions.domain

import com.epmedu.animeal.common.domain.ApplicationSettingsRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class GetCameraPermissionRequestedUseCase(
    private val repository: ApplicationSettingsRepository
) {
    operator fun invoke() = runBlocking {
        repository.getAppSettings().first().isCameraPermissionRequested
    }
}
