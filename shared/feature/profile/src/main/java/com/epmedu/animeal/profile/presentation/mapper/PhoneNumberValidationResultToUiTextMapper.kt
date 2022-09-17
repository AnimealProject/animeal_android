package com.epmedu.animeal.profile.presentation.mapper

import com.epmedu.animeal.foundation.common.UiText
import com.epmedu.animeal.foundation.common.validation.result.PhoneNumberValidationResult
import com.epmedu.animeal.resources.R

class PhoneNumberValidationResultToUiTextMapper {

    fun map(result: PhoneNumberValidationResult): UiText {
        return when (result) {
            is PhoneNumberValidationResult.ValidPhoneNumber -> {
                UiText.Empty
            }
            is PhoneNumberValidationResult.TooShortPhoneNumberError -> {
                UiText.StringResource(R.string.profile_phone_number_too_short_error_msg)
            }
        }
    }
}