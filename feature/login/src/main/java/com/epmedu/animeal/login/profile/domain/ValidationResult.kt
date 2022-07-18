package com.epmedu.animeal.login.profile.domain

import com.epmedu.animeal.login.profile.presentation.ui.UiText

internal data class ValidationResult(
    val isSuccess: Boolean,
    val errorMessage: UiText? = null
)