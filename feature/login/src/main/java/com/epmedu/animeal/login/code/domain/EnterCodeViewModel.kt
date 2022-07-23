package com.epmedu.animeal.login.code.domain

import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.domain.StateViewModel
import com.epmedu.animeal.login.code.data.EnterCodeRepository
import com.epmedu.animeal.login.code.domain.model.EnterCodeState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@HiltViewModel
internal class EnterCodeViewModel @Inject constructor(
    private val repository: EnterCodeRepository
) : StateViewModel<EnterCodeState>(EnterCodeState()) {

    private val _isCodeCorrect = MutableStateFlow(false)
    val isCodeCorrect: StateFlow<Boolean> = _isCodeCorrect.asStateFlow()

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
            _isCodeCorrect.value = isCodeCorrect
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