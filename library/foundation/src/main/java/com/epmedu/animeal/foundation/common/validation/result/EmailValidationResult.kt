package com.epmedu.animeal.foundation.common.validation.result

sealed interface EmailValidationResult {
    data object ValidEmail : EmailValidationResult
    data object BlankEmailError : EmailValidationResult
    data object InvalidEmailError : EmailValidationResult
    data class WrongEmailLengthError(val requiredLength: IntRange) : EmailValidationResult
}
