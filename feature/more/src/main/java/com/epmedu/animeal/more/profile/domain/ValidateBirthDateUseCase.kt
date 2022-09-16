package com.epmedu.animeal.more.profile.domain

import com.epmedu.animeal.foundation.common.validation.validator.ProfileValidator

internal class ValidateBirthDateUseCase(private val validator: ProfileValidator) {
    fun execute(birthDate: String) = validator.validateBirthDate(birthDate)
}