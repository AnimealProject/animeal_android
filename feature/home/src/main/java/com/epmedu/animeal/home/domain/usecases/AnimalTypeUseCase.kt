package com.epmedu.animeal.home.domain.usecases

import com.epmedu.animeal.common.constants.Arguments.ANIMAL_TYPE
import com.epmedu.animeal.common.domain.ApplicationSettingsRepository
import com.epmedu.animeal.common.domain.usecase.ForcedArgumentsUseCase
import com.epmedu.animeal.foundation.tabs.model.AnimalType
import kotlinx.coroutines.flow.first

class AnimalTypeUseCase(
    private val repository: ApplicationSettingsRepository,
    private val forcedArgumentsProvider: ForcedArgumentsUseCase
) {

    suspend operator fun invoke(): AnimalType =
        forcedArgumentsProvider<String>(ANIMAL_TYPE, hashCode())
            ?.let(AnimalType::valueOf)
            ?: getAnimalTypeFromSettings()

    private suspend fun getAnimalTypeFromSettings(): AnimalType {
        val type = repository.getAppSettings().first().animalType
        return AnimalType.getByName(type)
    }
}