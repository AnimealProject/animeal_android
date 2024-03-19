package com.epmedu.animeal.permissions.domain

import com.epmedu.animeal.common.component.AppSettingsUpdateScope
import com.epmedu.animeal.common.domain.ApplicationSettingsRepository

class UpdateAppSettingsUseCase(
    private val repository: ApplicationSettingsRepository
) {

    suspend operator fun invoke(updateScope: AppSettingsUpdateScope.() -> Unit) {
        repository.updateAppSettings(updateScope)
    }
}