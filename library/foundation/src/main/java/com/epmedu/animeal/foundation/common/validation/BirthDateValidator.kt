package com.epmedu.animeal.foundation.common.validation

import android.annotation.SuppressLint
import com.epmedu.animeal.foundation.common.UiText
import com.epmedu.animeal.resources.R
import java.text.SimpleDateFormat
import java.util.regex.Pattern

object BirthDateValidator : Validator {
    private const val BIRTH_DATE_REGEX = "^(0[1-9]|[12][0-9]|[3][01]).(0[1-9]|1[012]).\\d{4}$"
    private const val DATE_FORMAT = "dd.MM.yyyy"

    override fun validate(value: String): ValidationResult {
        return when {
            value.isBlank() -> {
                ValidationResult(
                    isSuccess = false,
                    errorMessage = UiText.StringResource(R.string.profile_select_birth_date)
                )
            }
            !isBirthdateValid(value) -> {
                ValidationResult(
                    isSuccess = false,
                    errorMessage = UiText.StringResource(R.string.profile_birthdate_error_msg)
                )
            }
            else -> ValidationResult(isSuccess = true)
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun isBirthdateValid(strDate: String): Boolean {
        var isValid = false
        if (Pattern.matches(BIRTH_DATE_REGEX, strDate)) {
            val sdf = SimpleDateFormat(DATE_FORMAT)
            sdf.isLenient = false
            kotlin.runCatching {
                sdf.parse(strDate)
                isValid = true
            }
        }
        return isValid
    }
}