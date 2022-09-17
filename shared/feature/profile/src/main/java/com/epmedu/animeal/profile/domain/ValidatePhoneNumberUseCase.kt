package com.epmedu.animeal.profile.domain

import com.epmedu.animeal.foundation.common.validation.validator.ProfileValidator

class ValidatePhoneNumberUseCase(private val validator: ProfileValidator) {
    fun execute(phoneNumber: String) = validator.validatePhoneNumber(phoneNumber)
}