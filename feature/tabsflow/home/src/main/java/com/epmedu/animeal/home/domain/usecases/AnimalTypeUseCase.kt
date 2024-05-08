package com.epmedu.animeal.home.domain.usecases

import com.epmedu.animeal.common.constants.Arguments.ANIMAL_TYPE
import com.epmedu.animeal.common.domain.usecase.ForcedArgumentsUseCase
import com.epmedu.animeal.feeding.domain.usecase.GetAnimalTypeFromSettingsUseCase
import com.epmedu.animeal.foundation.tabs.model.AnimalType

class AnimalTypeUseCase(
    private val getAnimalTypeFromSettingsUseCase: GetAnimalTypeFromSettingsUseCase,
    private val forcedArgumentsProvider: ForcedArgumentsUseCase
) {

    suspend operator fun invoke(): AnimalType =
        forcedArgumentsProvider<String>(ANIMAL_TYPE, hashCode())
            ?.let(AnimalType::valueOf)
            ?: getAnimalTypeFromSettingsUseCase()
}