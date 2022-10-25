package com.epmedu.animeal.signup.entercode.presentation.viewmodel

import com.epmedu.animeal.signup.entercode.presentation.viewmodel.EnterCodeViewModel.Companion.RESEND_DELAY
import com.epmedu.animeal.signup.entercode.presentation.viewmodel.EnterCodeViewModel.Companion.emptyCode

internal data class EnterCodeState(
    val code: List<Int?> = emptyCode(),
    val phoneNumber: String = "",
    val isError: Boolean = false,
    val isResendEnabled: Boolean = false,
    val resendDelay: Long = RESEND_DELAY
) {
    fun isCodeFilled() = code.all { it != null }

    fun isCodeChanged(codeToCompare: List<Int?>) = code != codeToCompare
}