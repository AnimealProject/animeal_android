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
import com.epmedu.animeal.common.presentation.viewmodel.handler.loading.LoadingHandler
import com.epmedu.animeal.common.timer.tickerFlow
import com.epmedu.animeal.networkuser.domain.usecase.GetNetworkProfileUseCase
import com.epmedu.animeal.networkuser.domain.usecase.authenticationtype.GetAuthenticationTypeUseCase
import com.epmedu.animeal.networkuser.domain.usecase.authenticationtype.SetFacebookAuthenticationTypeUseCase
import com.epmedu.animeal.profile.domain.SaveProfileUseCase
import com.epmedu.animeal.signup.entercode.domain.FacebookConfirmCodeUseCase
import com.epmedu.animeal.signup.entercode.domain.GetPhoneNumberUseCase
import com.epmedu.animeal.signup.entercode.domain.MobileConfirmCodeUseCase
import com.epmedu.animeal.signup.entercode.domain.SendCodeUseCase
import com.epmedu.animeal.signup.entercode.presentation.EnterCodeScreenEvent
import com.epmedu.animeal.signup.entercode.presentation.EnterCodeScreenEvent.NumberChanged
import com.epmedu.animeal.signup.entercode.presentation.EnterCodeScreenEvent.ReadSMS
import com.epmedu.animeal.signup.entercode.presentation.EnterCodeScreenEvent.ResendCode
import com.epmedu.animeal.signup.entercode.presentation.EnterCodeScreenEvent.ScreenDisplayed
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
    private val loadingHandler: LoadingHandler,
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

    fun handleEvents(event: EnterCodeScreenEvent) {
        when (event) {
            is ScreenDisplayed -> loadingHandler.hideLoading()
            is NumberChanged -> changeNumber(event.position, event.number)
            is ResendCode -> resendCode()
            is ReadSMS -> parseCodeFromSMS(event.message)
        }
    }

    private fun parseCodeFromSMS(message: String) {
        val code = message.filter { it.isDigit() }.map { it.digitToInt() }
        updateState { copy(code = code.toImmutableList()) }
    }

    private fun changeNumber(
        position: Int,
        number: String?
    ) {
        updateState { copy(code = getNewCodeWithReplacedNumber(position, number).toImmutableList()) }
    }

    private fun resendCode() {
        updateState { copy(code = emptyCode(), isResendEnabled = false) }
        viewModelScope.launch { launchResendTimer() }
        viewModelScope.launch { sendCodeUseCase() }
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
                loadingHandler.showLoading()
                lastCode = state.code
                when (authenticationType) {
                    AuthenticationType.Mobile -> confirmSignIn()
                    is AuthenticationType.Facebook -> confirmResendCode()
                }
            }
        }
    }

    private fun updateIsError() {
        updateState { copy(isError = state.code == emptyCode() && state.isError) }
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
                    loadingHandler.hideLoading()
                    updateState { copy(code = emptyCode(), isError = true) }
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
                    loadingHandler.hideLoading()
                    updateState { copy(isError = true) }
                }
            )
        }
    }

    private fun getNewCodeWithReplacedNumber(
        position: Int,
        newNumber: String?
    ): List<Int?> {
        val code = state.code.toMutableList()

        newNumber?.let {
            val numberToPaste = when {
                position + newNumber.length > CODE_SIZE -> {
                    newNumber.substring(0, CODE_SIZE - position)
                }
                else -> {
                    newNumber
                }
            }
            numberToPaste.forEachIndexed { index, char ->
                code[position + index] = char.digitToInt()
            }
        } ?: run {
            code[position] = null
        }
        return code
    }

    companion object {
        const val RESEND_DELAY = MINUTE_IN_MILLIS / 2

        private const val CODE_SIZE = 6

        internal fun emptyCode() = List(CODE_SIZE) { null }.toImmutableList()
    }
}