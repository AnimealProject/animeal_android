package com.epmedu.animeal.signup.entercode.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultEventDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.EventDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.signup.entercode.data.EnterCodeRepository
import com.epmedu.animeal.signup.entercode.presentation.viewmodel.EnterCodeEvent.NavigateToFinishProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class EnterCodeViewModel @Inject constructor(
    private val repository: EnterCodeRepository
) : ViewModel(),
    StateDelegate<EnterCodeState> by DefaultStateDelegate(initialState = EnterCodeState()),
    EventDelegate<EnterCodeEvent> by DefaultEventDelegate() {

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
        validateCodeIfFilled()
    }

    private fun getNewCodeWithReplacedDigit(position: Int, newDigit: Int?): List<Int?> {
        return state.code.mapIndexed { index, currentDigit ->
            if (index == position) newDigit
            else currentDigit
        }
    }

    private fun validateCodeIfFilled() {
        if (state.isCodeFilled()) {
            validateCode()
        }
    }

    private fun validateCode() {
        updateState {
            copy(
                isError = if (isCodeEquals(CORRECT_CODE)) {
                    navigateToFinishProfile()
                    false
                } else {
                    true
                }
            )
        }
    }

    private fun navigateToFinishProfile() {
        viewModelScope.launch {
            sendEvent(NavigateToFinishProfile)
        }
    }

    companion object {
        const val RESEND_DELAY = 30L

        private const val CODE_SIZE = 4
        private const val CORRECT_CODE = "1111"

        internal fun emptyCode() = List(CODE_SIZE) { null }
    }
}