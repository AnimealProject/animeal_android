package com.epmedu.animeal.foundation.common.validation.result

sealed interface NameValidationResult {
    data object ValidName : NameValidationResult
    data object BlankNameError : NameValidationResult
    data class WrongNameLengthError(val requiredLength: IntRange) : NameValidationResult
    data object InvalidNameError : NameValidationResult
}