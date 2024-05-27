package com.epmedu.animeal.feedings.domain.usecase

import com.epmedu.animeal.common.domain.ApplicationSettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetViewedFeedingsUseCase(
    private val applicationSettingsRepository: ApplicationSettingsRepository
) {
    operator fun invoke(): Flow<Set<String>> {
        return applicationSettingsRepository.getAppSettings()
            .map { appSettings ->
                appSettings.viewedFeedingIds
            }
    }
}