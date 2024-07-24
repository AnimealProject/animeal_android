package com.epmedu.animeal.signup.entercode.presentation.viewmodel

import com.epmedu.animeal.signup.entercode.presentation.viewmodel.EnterCodeViewModel.Companion.RESEND_DELAY
import com.epmedu.animeal.signup.entercode.presentation.viewmodel.EnterCodeViewModel.Companion.emptyCode
import kotlinx.collections.immutable.ImmutableList

internal data class EnterCodeState(
    val code: ImmutableList<Int?> = emptyCode(),
    val phoneNumber: String = "",
    val isError: Boolean = false,
    val isResendEnabled: Boolean = false,
    val resendDelay: Long = RESEND_DELAY,
    val nextDestination: EnterCodeNextDestination? = null
) {
    fun isCodeFilled() = code.all { it != null }

    fun isCodeChanged(codeToCompare: List<Int?>) = code != codeToCompare
}