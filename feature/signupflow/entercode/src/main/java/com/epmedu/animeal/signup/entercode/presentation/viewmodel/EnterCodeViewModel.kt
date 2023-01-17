package com.epmedu.animeal.signup.entercode.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.auth.AuthenticationType
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultEventDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.EventDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.networkuser.domain.authenticationtype.GetAuthenticationTypeUseCase
import com.epmedu.animeal.signup.entercode.domain.*
import com.epmedu.animeal.signup.entercode.domain.FacebookConfirmCodeUseCase
import com.epmedu.animeal.signup.entercode.domain.MobileConfirmCodeUseCase
import com.epmedu.animeal.signup.entercode.presentation.viewmodel.EnterCodeEvent.NavigateToFinishProfile
import com.epmedu.animeal.signup.entercode.presentation.viewmodel.EnterCodeEvent.NavigateToHomeScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class EnterCodeViewModel @Inject constructor(
    private val getAuthenticationTypeUseCase: GetAuthenticationTypeUseCase,
    private val sendCodeUseCase: SendCodeUseCase,
    private val mobileConfirmCodeUseCase: MobileConfirmCodeUseCase,
    private val facebookConfirmCodeUseCase: FacebookConfirmCodeUseCase,
    private val getPhoneNumberUseCase: GetPhoneNumberUseCase,
) : ViewModel(),
    StateDelegate<EnterCodeState> by DefaultStateDelegate(initialState = EnterCodeState()),
    EventDelegate<EnterCodeEvent> by DefaultEventDelegate() {

    private var authenticationType: AuthenticationType = AuthenticationType.Mobile

    private var lastCode: List<Int?> = emptyCode()

    init {
        viewModelScope.launch { getPhoneNumber() }
        viewModelScope.launch { launchResendTimer() }
        viewModelScope.launch { launchCodeValidation() }
        viewModelScope.launch { loadAuthenticationType() }
    }

    fun resendCode() {
        updateState { copy(code = emptyCode(), isResendEnabled = false) }
        viewModelScope.launch { launchResendTimer() }
        viewModelScope.launch { sendCodeUseCase({}, {}) }
    }

    fun changeDigit(
        position: Int,
        digit: Int?
    ) {
        updateState { copy(code = getNewCodeWithReplacedDigit(position, digit).toImmutableList()) }
    }

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

    private suspend fun loadAuthenticationType() {
        viewModelScope.launch {
            authenticationType = getAuthenticationTypeUseCase()
        }
    }

    private suspend fun launchCodeValidation() {
        stateFlow.collect {
            updateIsError()
            if (state.isCodeFilled() && state.isCodeChanged(lastCode)) {
                lastCode = state.code
                when (authenticationType) {
                    AuthenticationType.Mobile -> confirmSignIn()
                    AuthenticationType.Facebook -> confirmResendCode()
                }
            }
        }
    }

    private fun updateIsError() {
        updateState { copy(isError = isCodeFilled() && state.isError) }
    }

    private fun confirmSignIn() {
        viewModelScope.launch {
            mobileConfirmCodeUseCase(
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

    private fun confirmResendCode() {
        viewModelScope.launch {
            facebookConfirmCodeUseCase(
                code = state.code,
                onSuccess = {
                    updateState { copy(isError = false) }
                    viewModelScope.launch { sendEvent(NavigateToHomeScreen) }
                },
                onError = {
                    updateState { copy(isError = true) }
                },
            )
        }
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

    companion object {
        const val RESEND_DELAY = 30L

        private const val CODE_SIZE = 6

        internal fun emptyCode() = List(CODE_SIZE) { null }.toImmutableList()
    }
}