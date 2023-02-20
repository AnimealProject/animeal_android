package com.epmedu.animeal.foundation.common.validation.validator

import com.epmedu.animeal.foundation.common.validation.Constants.GE_PHONE_NUMBER_LENGTH
import com.epmedu.animeal.foundation.common.validation.result.PhoneNumberValidationResult.TooShortPhoneNumberError
import com.epmedu.animeal.foundation.common.validation.result.PhoneNumberValidationResult.ValidPhoneNumber

internal object PhoneNumberValidator {
    fun validate(value: String) = when {
        value.length < GE_PHONE_NUMBER_LENGTH -> TooShortPhoneNumberError
        else -> ValidPhoneNumber
    }
}