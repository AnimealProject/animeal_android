package com.epmedu.animeal.foundation.common.validation

interface ProfileValidator {
    fun validateName(name: String): ValidationResult
    fun validateSurname(surname: String): ValidationResult
    fun validateEmail(email: String): ValidationResult
    fun validateBirthDate(birthDate: String): ValidationResult
}