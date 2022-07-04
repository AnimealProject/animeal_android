package com.epmedu.animeal.feature.login.code

import com.epmedu.animeal.feature.login.code.CodeConfirmationViewModel.Companion.RESEND_DELAY

internal data class CodeConfirmationScreenState(
    val phoneNumber: String,
    val code: List<Int?>,
    val isCodeCorrect: Boolean? = null,
    val isResendEnabled: Boolean = false,
    val resendDelay: Long = RESEND_DELAY
)