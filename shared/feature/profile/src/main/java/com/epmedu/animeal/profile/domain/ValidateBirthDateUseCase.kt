package com.epmedu.animeal.profile.domain

import com.epmedu.animeal.foundation.common.validation.validator.ProfileValidator

class ValidateBirthDateUseCase(private val validator: ProfileValidator) {
    fun execute(birthDate: String) = validator.validateBirthDate(birthDate)
}