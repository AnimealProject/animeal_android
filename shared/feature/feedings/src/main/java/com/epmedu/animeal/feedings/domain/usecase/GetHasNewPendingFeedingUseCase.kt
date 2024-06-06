package com.epmedu.animeal.feedings.domain.usecase

import com.epmedu.animeal.common.domain.ApplicationSettingsRepository
import com.epmedu.animeal.feeding.domain.model.FeedingStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetHasNewPendingFeedingUseCase(
    private val getAllFeedingsUseCase: GetAllFeedingsUseCase,
    private val applicationSettingsRepository: ApplicationSettingsRepository
) {

    operator fun invoke(shouldFetch: Boolean): Flow<Boolean> {
        return combine(
            applicationSettingsRepository.getAppSettings(),
            getAllFeedingsUseCase(shouldFetch)
        ) { appSettings, feedings ->
            feedings.any { feeding ->
                feeding.status == FeedingStatus.Pending &&
                    appSettings.viewedFeedingIds.contains(feeding.id).not()
            }
        }
    }
}