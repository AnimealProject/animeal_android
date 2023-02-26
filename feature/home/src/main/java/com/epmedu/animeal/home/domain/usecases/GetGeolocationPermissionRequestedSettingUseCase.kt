package com.epmedu.animeal.home.domain.usecases

import com.epmedu.animeal.home.data.ApplicationSettingsRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class GetGeolocationPermissionRequestedSettingUseCase(
    private val repository: ApplicationSettingsRepository,
) {
    operator fun invoke() = runBlocking {
        repository.getAppSettings().first().isInitialGeolocationPermissionRequested
    }
}
