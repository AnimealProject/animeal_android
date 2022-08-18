package com.epmedu.animeal.login.code.domain.model

import com.epmedu.animeal.login.code.domain.EnterCodeViewModel.Companion.RESEND_DELAY
import com.epmedu.animeal.login.code.domain.EnterCodeViewModel.Companion.emptyCode

internal data class EnterCodeState(
    val code: List<Int?> = emptyCode(),
    val phoneNumber: String = "",
    val isError: Boolean = false,
    val isResendEnabled: Boolean = false,
    val resendDelay: Long = RESEND_DELAY
) {
    fun isCodeFilled() = code.all { it != null }

    fun isCodeEquals(stringToCompare: String) = code.joinToString("") == stringToCompare
}