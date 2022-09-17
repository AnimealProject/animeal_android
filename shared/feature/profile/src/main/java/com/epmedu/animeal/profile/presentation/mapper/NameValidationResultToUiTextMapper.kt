package com.epmedu.animeal.profile.presentation.mapper

import com.epmedu.animeal.foundation.common.UiText
import com.epmedu.animeal.foundation.common.validation.result.NameValidationResult
import com.epmedu.animeal.resources.R

class NameValidationResultToUiTextMapper {

    fun map(result: NameValidationResult): UiText {
        return when (result) {
            is NameValidationResult.ValidName -> {
                UiText.Empty
            }
            is NameValidationResult.BlankNameError -> {
                UiText.StringResource(R.string.profile_name_blank_error_msg)
            }
            is NameValidationResult.WrongNameLengthError -> {
                UiText.StringResource(
                    R.string.profile_wrong_amount_of_characters_error_msg,
                    result.requiredLength.first,
                    result.requiredLength.last
                )
            }
        }
    }
}