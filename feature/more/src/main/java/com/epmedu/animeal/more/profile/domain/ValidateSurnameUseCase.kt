package com.epmedu.animeal.more.profile.domain

import com.epmedu.animeal.foundation.common.validation.validator.ProfileValidator

class ValidateSurnameUseCase(private val validator: ProfileValidator) {
    fun execute(surname: String) = validator.validateSurname(surname)
}