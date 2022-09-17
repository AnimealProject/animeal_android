package com.epmedu.animeal.profile.domain

import com.epmedu.animeal.foundation.common.validation.validator.ProfileValidator

class ValidateNameUseCase(private val validator: ProfileValidator) {
    fun execute(name: String) = validator.validateName(name)
}