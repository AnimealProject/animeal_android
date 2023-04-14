package com.epmedu.animeal.home.domain.usecases

import androidx.lifecycle.SavedStateHandle
import com.epmedu.animeal.common.constants.Arguments.ANIMAL_TYPE
import com.epmedu.animeal.foundation.tabs.model.AnimalType
import com.epmedu.animeal.home.domain.ApplicationSettingsRepository
import kotlinx.coroutines.flow.first

class AnimalTypeUseCase(
    private val savedStateHandle: SavedStateHandle,
    private val repository: ApplicationSettingsRepository
) {

    suspend operator fun invoke(): AnimalType =
        if (savedStateHandle.contains(ANIMAL_TYPE)) {
            savedStateHandle.get<String>(ANIMAL_TYPE)?.let(AnimalType::valueOf) ?: getAnimalTypeFromSettings()
        } else getAnimalTypeFromSettings()

    private suspend fun getAnimalTypeFromSettings(): AnimalType {
        val type = repository.getAppSettings().first().animalType
        return AnimalType.getByName(type)
    }
}