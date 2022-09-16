package com.epmedu.animeal.foundation.common.validation.validator

import com.epmedu.animeal.foundation.common.validation.Constants.MAX_SURNAME_LENGTH
import com.epmedu.animeal.foundation.common.validation.Constants.MIN_SURNAME_LENGTH
import com.epmedu.animeal.foundation.common.validation.result.SurnameValidationResult.BlankSurnameError
import com.epmedu.animeal.foundation.common.validation.result.SurnameValidationResult.ValidSurname
import com.epmedu.animeal.foundation.common.validation.result.SurnameValidationResult.WrongSurnameLengthError

internal object SurnameValidator {
    private val requiredLength = MIN_SURNAME_LENGTH..MAX_SURNAME_LENGTH

    fun validate(value: String) = when {
        value.isBlank() -> BlankSurnameError
        value.length !in requiredLength -> WrongSurnameLengthError(requiredLength)
        else -> ValidSurname
    }
}