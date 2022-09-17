package com.epmedu.animeal.profile.presentation.mapper

import com.epmedu.animeal.foundation.common.UiText
import com.epmedu.animeal.foundation.common.validation.result.EmailValidationResult
import com.epmedu.animeal.resources.R

class EmailValidationResultToUiTextMapper {

    fun map(result: EmailValidationResult): UiText {
        return when (result) {
            is EmailValidationResult.ValidEmail -> {
                UiText.Empty
            }
            is EmailValidationResult.BlankEmailError -> {
                UiText.StringResource(R.string.profile_email_blank_error_msg)
            }
            is EmailValidationResult.WrongEmailLengthError -> {
                UiText.StringResource(
                    R.string.profile_wrong_amount_of_characters_error_msg,
                    result.requiredLength.first,
                    result.requiredLength.last
                )
            }
            is EmailValidationResult.InvalidEmailError -> {
                UiText.StringResource(R.string.profile_email_invalid_error_msg)
            }
        }
    }
}