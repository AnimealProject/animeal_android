package com.epmedu.animeal.foundation.common.validation

interface Validator {

    fun validate(value: String): ValidationResult
}