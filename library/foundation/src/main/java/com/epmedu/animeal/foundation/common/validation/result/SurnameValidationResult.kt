package com.epmedu.animeal.foundation.common.validation.result

sealed interface SurnameValidationResult {
    object ValidSurname : SurnameValidationResult
    object BlankSurnameError : SurnameValidationResult
    data class WrongSurnameLengthError(val requiredLength: IntRange) : SurnameValidationResult
}