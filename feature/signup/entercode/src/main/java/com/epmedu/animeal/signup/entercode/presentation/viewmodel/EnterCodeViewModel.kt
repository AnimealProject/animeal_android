package com.epmedu.animeal.signup.entercode.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.domain.StateViewModel
import com.epmedu.animeal.signup.entercode.data.EnterCodeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class EnterCodeViewModel @Inject constructor(
    private val repository: EnterCodeRepository
) : StateViewModel<EnterCodeState, EnterCodeEvent>(EnterCodeState()) {

    init {
        viewModelScope.launch { getPhoneNumber() }
        viewModelScope.launch { launchResendTimer() }
    }

    private suspend fun launchResendTimer() {
        for (tick in RESEND_DELAY downTo 1L) {
            updateState { copy(resendDelay = tick) }
            delay(1000)
        }
        updateState { copy(isResendEnabled = true, resendDelay = 0) }
    }

    private suspend fun getPhoneNumber() {
        repository.phoneNumber.collectLatest {
            updateState { copy(phoneNumber = it) }
        }
    }

    fun resendCode() {
        viewModelScope.launch {
            clearCodeAndDisableResend()
            launchResendTimer()
        }
    }

    private fun clearCodeAndDisableResend() {
        updateState { copy(code = emptyCode(), isResendEnabled = false) }
    }

    fun changeDigit(position: Int, digit: Int?) {
        updateState {
            copy(
                code = getNewCodeWithReplacedDigit(position, digit)
            )
        }
        validateCodeIfFull()
    }

    private fun getNewCodeWithReplacedDigit(position: Int, newDigit: Int?): List<Int?> {
        return state.code.mapIndexed { index, currentDigit ->
            if (index == position) newDigit
            else currentDigit
        }
    }

    private fun validateCodeIfFull() {
        if (state.code.all { it != null }) {
            val isCodeCorrect = isCodeCorrect()
            updateState { copy(isError = !isCodeCorrect) }

            viewModelScope.launch {
                sendEvent(EnterCodeEvent.NavigateToFinishProfile)
            }
        }
    }

    private fun isCodeCorrect(): Boolean {
        val codeString = state.code.joinToString("")
        return codeString == CORRECT_CODE
    }

    companion object {
        const val RESEND_DELAY = 30L

        private const val CODE_SIZE = 4
        private const val CORRECT_CODE = "1111"

        internal fun emptyCode() = List(CODE_SIZE) { null }
    }
}