package com.epmedu.animeal.login.profile.domain

import android.annotation.SuppressLint
import com.epmedu.animeal.login.profile.presentation.ui.UiText
import com.epmedu.animeal.resources.R
import java.text.SimpleDateFormat
import java.util.regex.Pattern

internal object BirthDateValidator : Validator {

    private const val BIRTH_DATE_REGEX = "^(0[1-9]|[12][0-9]|[3][01]).(0[1-9]|1[012]).\\d{4}$"
    private const val DATE_FORMAT = "dd.MM.yyyy"

    @Suppress("ReturnCount")
    override fun validate(value: String): ValidationResult {
        if (value.isBlank()) {
            return ValidationResult(
                isSuccess = false,
                errorMessage = UiText.StringResource(R.string.profile_select_birth_date)
            )
        }
        if (!isBirthdateValid(value)) {
            return ValidationResult(
                isSuccess = false,
                errorMessage = UiText.StringResource(R.string.profile_birthdate_error_msg)
            )
        }
        return ValidationResult(isSuccess = true)
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