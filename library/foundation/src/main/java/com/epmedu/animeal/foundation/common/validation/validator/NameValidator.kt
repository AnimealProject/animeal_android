package com.epmedu.animeal.foundation.common.validation.validator

import com.epmedu.animeal.foundation.common.validation.Constants.MAX_NAME_LENGTH
import com.epmedu.animeal.foundation.common.validation.Constants.MIN_NAME_LENGTH
import com.epmedu.animeal.foundation.common.validation.result.NameValidationResult.BlankNameError
import com.epmedu.animeal.foundation.common.validation.result.NameValidationResult.ValidName
import com.epmedu.animeal.foundation.common.validation.result.NameValidationResult.WrongNameLengthError

internal object NameValidator {
    private val requiredLength = MIN_NAME_LENGTH..MAX_NAME_LENGTH

    fun validate(value: String) = when {
        value.isBlank() -> BlankNameError
        value.length !in requiredLength -> WrongNameLengthError(requiredLength)
        else -> ValidName
    }
}