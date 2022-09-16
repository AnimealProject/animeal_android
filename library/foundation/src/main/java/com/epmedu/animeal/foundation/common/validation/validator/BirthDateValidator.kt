package com.epmedu.animeal.foundation.common.validation.validator

import com.epmedu.animeal.foundation.common.validation.result.BirthDateValidationResult.BlankBirthDateError
import com.epmedu.animeal.foundation.common.validation.result.BirthDateValidationResult.ValidBirthDate

internal object BirthDateValidator {

    fun validate(value: String) = when {
        value.isBlank() -> BlankBirthDateError
        else -> ValidBirthDate
    }
}