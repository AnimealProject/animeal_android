package com.epmedu.animeal.foundation.common.validation.result

sealed interface SurnameValidationResult {
    data object ValidSurname : SurnameValidationResult
    data object BlankSurnameError : SurnameValidationResult
    data class WrongSurnameLengthError(val requiredLength: IntRange) : SurnameValidationResult
    data object InvalidSurnameError : SurnameValidationResult
}