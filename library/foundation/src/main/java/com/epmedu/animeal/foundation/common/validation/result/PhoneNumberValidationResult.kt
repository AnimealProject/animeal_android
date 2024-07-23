package com.epmedu.animeal.foundation.common.validation.result

sealed interface PhoneNumberValidationResult {
    data object TooShortPhoneNumberError : PhoneNumberValidationResult
    data object ValidPhoneNumber : PhoneNumberValidationResult
}