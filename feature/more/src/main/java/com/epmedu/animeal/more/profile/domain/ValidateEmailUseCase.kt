package com.epmedu.animeal.more.profile.domain

import com.epmedu.animeal.foundation.common.validation.validator.ProfileValidator

internal class ValidateEmailUseCase(private val validator: ProfileValidator) {
    fun execute(email: String) = validator.validateEmail(email)
}