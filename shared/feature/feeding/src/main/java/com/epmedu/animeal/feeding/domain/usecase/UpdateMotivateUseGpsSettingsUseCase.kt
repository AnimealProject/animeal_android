package com.epmedu.animeal.feeding.domain.usecase

import com.epmedu.animeal.common.component.AppSettingsProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdateMotivateUseGpsSettingsUseCase @Inject constructor(
    private val appSettingsProvider: AppSettingsProvider,
){
    suspend operator fun invoke(isAsked: Boolean) {
        appSettingsProvider.updateAppSettings {
            isMotivatedUseGps = isAsked
        }
    }
}