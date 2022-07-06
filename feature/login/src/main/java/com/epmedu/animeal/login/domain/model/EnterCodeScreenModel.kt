package com.epmedu.animeal.login.domain.model

import com.epmedu.animeal.login.domain.EnterCodeViewModel.Companion.RESEND_DELAY

internal data class EnterCodeScreenModel(
    val phoneNumber: String,
    val code: List<Int?>,
    val isError: Boolean = false,
    val isResendEnabled: Boolean = false,
    val resendDelay: Long = RESEND_DELAY
)