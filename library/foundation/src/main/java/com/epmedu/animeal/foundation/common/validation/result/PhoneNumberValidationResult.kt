package com.epmedu.animeal.foundation.common.validation.result

sealed interface PhoneNumberValidationResult {
    object TooShortPhoneNumberError : PhoneNumberValidationResult
    object ValidPhoneNumber : PhoneNumberValidationResult
}