package com.epmedu.animeal.foundation.common.validation

class ProfileValidator {
    private val nameValidator: NameValidator = NameValidator
    private val surnameValidator: SurnameValidator = SurnameValidator
    private val emailValidator: EmailValidator = EmailValidator
    private val birthDateValidator: BirthDateValidator = BirthDateValidator

    fun validateName(name: String): ValidationResult = nameValidator.validate(name)

    fun validateSurname(surname: String): ValidationResult = surnameValidator.validate(surname)

    fun validateEmail(email: String): ValidationResult = emailValidator.validate(email)

    fun validateBirthDate(birthDate: String): ValidationResult = birthDateValidator.validate(birthDate)
}