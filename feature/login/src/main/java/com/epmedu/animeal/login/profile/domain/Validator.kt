package com.epmedu.animeal.login.profile.domain

internal interface Validator {

    fun validate(value: String): ValidationResult
}