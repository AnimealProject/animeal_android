package com.epmedu.animeal.permissions.domain

import com.epmedu.animeal.common.domain.ApplicationSettingsRepository

class GetAppSettingsUseCase(
    private val repository: ApplicationSettingsRepository
) {

    operator fun invoke() = repository.getAppSettings()
}