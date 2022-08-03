package com.epmedu.animeal.foundation.common.validation

import com.epmedu.animeal.foundation.common.UiText
import com.epmedu.animeal.resources.R
import java.time.format.DateTimeFormatter
import java.time.format.ResolverStyle
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

    private fun isBirthdateValid(strDate: String): Boolean {
        if (Pattern.matches(BIRTH_DATE_REGEX, strDate)) {
            val formatter = DateTimeFormatter
                .ofPattern(DATE_FORMAT)
                .withResolverStyle(ResolverStyle.LENIENT)

            return runCatching {
                formatter.parse(strDate)
            }.isSuccess
        }
        return false
    }
}