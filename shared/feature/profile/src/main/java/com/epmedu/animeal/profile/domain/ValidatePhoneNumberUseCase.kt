package com.epmedu.animeal.profile.domain

import com.epmedu.animeal.foundation.common.UiText
import com.epmedu.animeal.foundation.common.validation.result.PhoneNumberValidationResult.TooShortPhoneNumberError
import com.epmedu.animeal.foundation.common.validation.result.PhoneNumberValidationResult.ValidPhoneNumber
import com.epmedu.animeal.foundation.common.validation.validator.ProfileValidator
import com.epmedu.animeal.resources.R

class ValidatePhoneNumberUseCase(private val validator: ProfileValidator) {

    operator fun invoke(phoneNumber: String): UiText {
        return when (validator.validatePhoneNumber(phoneNumber)) {
            is ValidPhoneNumber -> {
                UiText.Empty
            }
            is TooShortPhoneNumberError -> {
                UiText.StringResource(R.string.profile_phone_number_too_short_error_msg)
            }
        }
    }
}