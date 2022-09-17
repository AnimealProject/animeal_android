package com.epmedu.animeal.profile.domain

import com.epmedu.animeal.foundation.common.validation.validator.ProfileValidator

class ValidateSurnameUseCase(private val validator: ProfileValidator) {
    fun execute(surname: String) = validator.validateSurname(surname)
}