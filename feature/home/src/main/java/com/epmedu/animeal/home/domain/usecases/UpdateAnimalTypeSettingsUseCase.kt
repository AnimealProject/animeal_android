package com.epmedu.animeal.home.domain.usecases

import com.epmedu.animeal.foundation.tabs.model.AnimalType
import com.epmedu.animeal.home.domain.ApplicationSettingsRepository

class UpdateAnimalTypeSettingsUseCase(
    private val repository: ApplicationSettingsRepository
) {

    suspend operator fun invoke(type: AnimalType) {
        repository.updateAppSettings {
            animalType = type.name
        }
    }
}