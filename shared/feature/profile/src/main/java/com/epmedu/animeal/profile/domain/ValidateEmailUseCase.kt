package com.epmedu.animeal.profile.domain

import com.epmedu.animeal.foundation.common.validation.validator.ProfileValidator

class ValidateEmailUseCase(private val validator: ProfileValidator) {
    fun execute(email: String) = validator.validateEmail(email)
}