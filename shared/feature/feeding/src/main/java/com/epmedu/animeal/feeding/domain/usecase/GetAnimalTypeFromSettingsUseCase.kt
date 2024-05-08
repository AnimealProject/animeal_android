package com.epmedu.animeal.feeding.domain.usecase

import com.epmedu.animeal.foundation.tabs.model.AnimalType
import com.epmedu.animeal.permissions.domain.GetAppSettingsUseCase
import kotlinx.coroutines.flow.first

class GetAnimalTypeFromSettingsUseCase(
    private val getAppSettingsUseCase: GetAppSettingsUseCase
) {

    suspend operator fun invoke(): AnimalType {
        return getAppSettingsUseCase().first().let {
            AnimalType.getByName(it.animalType)
        }
    }
}