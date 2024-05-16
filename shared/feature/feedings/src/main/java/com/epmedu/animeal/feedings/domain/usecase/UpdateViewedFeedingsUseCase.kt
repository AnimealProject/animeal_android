package com.epmedu.animeal.feedings.domain.usecase

import com.epmedu.animeal.common.domain.ApplicationSettingsRepository

class UpdateViewedFeedingsUseCase(
    private val applicationSettingsRepository: ApplicationSettingsRepository
) {

    suspend operator fun invoke(viewedFeedingId: String) {
        applicationSettingsRepository.updateAppSettings {
            viewedFeedingIds += viewedFeedingId
        }
    }
}