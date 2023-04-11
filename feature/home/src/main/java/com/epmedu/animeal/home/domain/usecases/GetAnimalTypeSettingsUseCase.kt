package com.epmedu.animeal.home.domain.usecases

import com.epmedu.animeal.foundation.tabs.model.AnimalType
import com.epmedu.animeal.home.data.ApplicationSettingsRepository
import kotlinx.coroutines.flow.first

class GetAnimalTypeSettingsUseCase(
    private val repository: ApplicationSettingsRepository
) {

    suspend operator fun invoke(): AnimalType {
        val type = repository.getAppSettings().first().animalType
        return AnimalType.getByName(type)
    }
}