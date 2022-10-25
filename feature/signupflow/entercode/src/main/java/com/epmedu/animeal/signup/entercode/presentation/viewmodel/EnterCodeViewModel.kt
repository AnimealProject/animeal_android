package com.epmedu.animeal.signup.entercode.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amplifyframework.auth.cognito.options.AWSCognitoAuthSignInOptions
import com.amplifyframework.auth.cognito.options.AuthFlowType
import com.amplifyframework.core.Amplify
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultEventDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.EventDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.foundation.input.PhoneFormatTransformation.PHONE_NUMBER_PREFIX
import com.epmedu.animeal.signup.entercode.data.EnterCodeRepository
import com.epmedu.animeal.signup.entercode.presentation.viewmodel.EnterCodeEvent.NavigateToFinishProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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
        viewModelScope.launch { launchCodeValidation() }
    }

    private var lastCode: List<Int?> = emptyCode()

    private suspend fun getPhoneNumber() {
        repository.phoneNumber.collect { updateState { copy(phoneNumber = it) } }
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
            viewModelScope.launch {
                confirmSignIn()
            }
        }
    }

    fun changeDigit(position: Int, digit: Int?) {
        updateState { copy(code = getNewCodeWithReplacedDigit(position, digit)) }
    }

    private fun getNewCodeWithReplacedDigit(position: Int, newDigit: Int?): List<Int?> {
        return state.code.mapIndexed { index, currentDigit ->
            when (index) {
                position -> newDigit
                else -> currentDigit
            }
        }
    }

    fun resendCode() {
        viewModelScope.launch {
            clearCodeAndDisableResend()
            launchResendTimer()
        }
    }

    private fun clearCodeAndDisableResend() {
        signIn()
        updateState { copy(code = emptyCode(), isResendEnabled = false) }
    }

    private fun confirmSignIn(){
        Log.d("AuthQuickstart", "state.phoneNumber -> ${state.phoneNumber}")

        Log.d("AuthQuickstart", "code -> ${state.code.joinToString("")}")
        Amplify.Auth.confirmSignIn(state.code.joinToString ( ""),
            { result ->
                updateState { copy(isError = false) }
                Log.d("AuthQuickstart confirm result", "$result")
                    viewModelScope.launch { sendEvent(NavigateToFinishProfile) }
            },
            {
                updateState { copy(isError = true) }
                Log.e("AuthQuickstart", "confirm error", it)
            }
        )
    }

    private fun signIn() {
        val phoneNumber = PHONE_NUMBER_PREFIX + state.phoneNumber

        val authSignInOptions = AWSCognitoAuthSignInOptions.builder()
            .authFlowType(AuthFlowType.CUSTOM_AUTH)
            .build()

        Amplify.Auth.signIn(
            phoneNumber, "", authSignInOptions,
            {
                Log.d("AuthQuickstart", "entercode signIn result -> $it")
            },
            {
                Log.d("AuthQuickstart", "entercode signIn error -> $it")
            }
        )
    }

    companion object {
        const val RESEND_DELAY = 30L

        private const val CODE_SIZE = 6

        internal fun emptyCode() = List(CODE_SIZE) { null }
    }
}