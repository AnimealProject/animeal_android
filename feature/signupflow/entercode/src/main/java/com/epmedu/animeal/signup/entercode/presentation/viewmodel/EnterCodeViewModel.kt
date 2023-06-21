package com.epmedu.animeal.signup.entercode.presentation.viewmodel

import android.text.format.DateUtils.MINUTE_IN_MILLIS
import android.text.format.DateUtils.SECOND_IN_MILLIS
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.auth.AuthenticationType
import com.epmedu.animeal.common.presentation.viewmodel.delegate.ActionDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultEventDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.EventDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.common.timer.tickerFlow
import com.epmedu.animeal.networkuser.domain.usecase.GetNetworkProfileUseCase
import com.epmedu.animeal.networkuser.domain.usecase.authenticationtype.GetAuthenticationTypeUseCase
import com.epmedu.animeal.networkuser.domain.usecase.authenticationtype.SetFacebookAuthenticationTypeUseCase
import com.epmedu.animeal.profile.domain.SaveProfileUseCase
import com.epmedu.animeal.signup.entercode.domain.FacebookConfirmCodeUseCase
import com.epmedu.animeal.signup.entercode.domain.GetPhoneNumberUseCase
import com.epmedu.animeal.signup.entercode.domain.MobileConfirmCodeUseCase
import com.epmedu.animeal.signup.entercode.domain.SendCodeUseCase
import com.epmedu.animeal.signup.entercode.presentation.viewmodel.EnterCodeEvent.NavigateToFinishProfile
import com.epmedu.animeal.signup.entercode.presentation.viewmodel.EnterCodeEvent.NavigateToHomeScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class EnterCodeViewModel @Inject constructor(
    private val actionDelegate: ActionDelegate,
    private val getAuthenticationTypeUseCase: GetAuthenticationTypeUseCase,
    private val sendCodeUseCase: SendCodeUseCase,
    private val mobileConfirmCodeUseCase: MobileConfirmCodeUseCase,
    private val facebookConfirmCodeUseCase: FacebookConfirmCodeUseCase,
    private val getPhoneNumberUseCase: GetPhoneNumberUseCase,
    private val getNetworkProfileUseCase: GetNetworkProfileUseCase,
    private val saveProfileUseCase: SaveProfileUseCase,
    private val setFacebookAuthenticationTypeUseCase: SetFacebookAuthenticationTypeUseCase,
) : ViewModel(),
    ActionDelegate by actionDelegate,
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
        viewModelScope.launch { sendCodeUseCase() }
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
        tickerFlow(RESEND_DELAY, SECOND_IN_MILLIS)
            .onEach {
                val secondsLeft = it / SECOND_IN_MILLIS
                updateState { copy(resendDelay = secondsLeft) }
            }
            .onCompletion { updateState { copy(isResendEnabled = true, resendDelay = 0) } }
            .collect()
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
                    is AuthenticationType.Facebook -> confirmResendCode()
                }
            }
        }
    }

    private fun updateIsError() {
        updateState { copy(isError = isCodeFilled() && state.isError) }
    }

    private fun confirmSignIn() {
        viewModelScope.launch {
            performAction(
                action = {
                    mobileConfirmCodeUseCase(code = state.code)
                },
                onSuccess = {
                    updateState { copy(isError = false) }
                    fetchNetworkProfile()
                },
                onError = {
                    updateState { copy(isError = true) }
                }
            )
        }
    }

    private fun fetchNetworkProfile() {
        viewModelScope.launch {
            getNetworkProfileUseCase()?.let { networkProfile ->
                saveProfileUseCase(networkProfile).collect {
                    sendEvent(
                        when {
                            networkProfile.isFilled() -> NavigateToHomeScreen
                            else -> NavigateToFinishProfile
                        }
                    )
                }
            } ?: sendEvent(NavigateToFinishProfile)
        }
    }

    private fun confirmResendCode() {
        viewModelScope.launch {
            performAction(
                action = {
                    facebookConfirmCodeUseCase(code = state.code)
                },
                onSuccess = {
                    updateState { copy(isError = false) }
                    viewModelScope.launch {
                        setFacebookAuthenticationTypeUseCase.invoke(isPhoneNumberVerified = true)
                    }
                    viewModelScope.launch { sendEvent(NavigateToHomeScreen) }
                },
                onError = {
                    updateState { copy(isError = true) }
                }
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
        const val RESEND_DELAY = MINUTE_IN_MILLIS / 2

        private const val CODE_SIZE = 6

        internal fun emptyCode() = List(CODE_SIZE) { null }.toImmutableList()
    }
}