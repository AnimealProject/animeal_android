package com.epmedu.animeal.foundation.common.validation.result

sealed interface BirthDateValidationResult {
    object ValidBirthDate : BirthDateValidationResult
    object BlankBirthDateError : BirthDateValidationResult
    object TooYoungBirthDateError : BirthDateValidationResult
}
