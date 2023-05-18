package com.epmedu.animeal.feeding.domain.usecase

import com.epmedu.animeal.common.domain.ApplicationSettingsRepository
import com.epmedu.animeal.foundation.tabs.model.AnimalType

class UpdateAnimalTypeSettingsUseCase(
    private val repository: ApplicationSettingsRepository
) {

    suspend operator fun invoke(type: AnimalType) {
        repository.updateAppSettings {
            animalType = type.name
        }
    }
}