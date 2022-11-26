package com.epmedu.animeal.signup.entercode.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultEventDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.EventDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.signup.entercode.domain.ConfirmCodeUseCase
import com.epmedu.animeal.signup.entercode.domain.GetPhoneNumberUseCase
import com.epmedu.animeal.signup.entercode.domain.SendCodeUseCase
import com.epmedu.animeal.signup.entercode.presentation.viewmodel.EnterCodeEvent.NavigateToFinishProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class EnterCodeViewModel @Inject constructor(
    private val sendCodeUseCase: SendCodeUseCase,
    private val confirmCodeUseCase: ConfirmCodeUseCase,
    private val getPhoneNumberUseCase: GetPhoneNumberUseCase,
) : ViewModel(),
    StateDelegate<EnterCodeState> by DefaultStateDelegate(initialState = EnterCodeState()),
    EventDelegate<EnterCodeEvent> by DefaultEventDelegate() {

    init {
        viewModelScope.launch { getPhoneNumber() }
        viewModelScope.launch { launchResendTimer() }
        viewModelScope.launch { launchCodeValidation() }
    }

    private var lastCode: List<Int?> = emptyCode()

    private suspend fun getPhoneNumber() {
        getPhoneNumberUseCase().collect { updateState { copy(phoneNumber = it) } }
    }

    private suspend fun launchResendTimer() {
        for (tick in RESEND_DELAY downTo 1L) {
            updateState { copy(resendDelay = tick) }
            delay(1000)
        }
        updateState { copy(isResendEnabled = true, resendDelay = 0) }
    }

    private suspend fun launchCodeValidation() {
        stateFlow.collect {
            updateIsError()
            navigateToFinishProfileIfCodeIsCorrect()
        }
    }

    private fun updateIsError() {
        updateState { copy(isError = isCodeFilled() && state.isError) }
    }

    private fun navigateToFinishProfileIfCodeIsCorrect() {
        if (state.isCodeFilled() && state.isCodeChanged(lastCode)) {
            lastCode = state.code
            confirmCode()
        }
    }

    private fun confirmCode() {
        viewModelScope.launch {
            confirmCodeUseCase(
                code = state.code,
                onSuccess = {
                    updateState { copy(isError = false) }
                    viewModelScope.launch { sendEvent(NavigateToFinishProfile) }
                },
                onError = {
                    updateState { copy(isError = true) }
                },
            )
        }
    }

    fun changeDigit(
        position: Int,
        digit: Int?
    ) {
        updateState { copy(code = getNewCodeWithReplacedDigit(position, digit)) }
    }

    private fun getNewCodeWithReplacedDigit(
        position: Int,
        newDigit: Int?
    ): List<Int?> {
        return state.code.mapIndexed { index, currentDigit ->
            when (index) {
                position -> newDigit
                else -> currentDigit
            }
        }
    }

    fun resendCode() {
        updateState { copy(code = emptyCode(), isResendEnabled = false) }
        viewModelScope.launch { launchResendTimer() }
        viewModelScope.launch { sendCodeUseCase({}, {}) }
    }

    companion object {
        const val RESEND_DELAY = 30L

        private const val CODE_SIZE = 6

        internal fun emptyCode() = List(CODE_SIZE) { null }
    }
}