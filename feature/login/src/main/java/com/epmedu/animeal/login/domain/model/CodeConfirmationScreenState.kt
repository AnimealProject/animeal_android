package com.epmedu.animeal.login.domain.model

import com.epmedu.animeal.login.domain.CodeConfirmationViewModel.Companion.RESEND_DELAY

internal data class CodeConfirmationScreenState(
    val phoneNumber: String,
    val code: List<Int?>,
    val isCodeCorrect: Boolean? = null,
    val isResendEnabled: Boolean = false,
    val resendDelay: Long = RESEND_DELAY
)