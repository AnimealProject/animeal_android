package com.epmedu.animeal.feeding.domain.usecase

import com.epmedu.animeal.common.component.AppSettingsProvider
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MotivateUseGpsSettingUseCase @Inject constructor(
    private val appSettingsProvider: AppSettingsProvider
) {
    suspend operator fun invoke(): Boolean {
        return appSettingsProvider.getAppSettings().first().isMotivatedUseGps
    }
}