package com.epmedu.animeal.signup.enterphone.presentation.viewmodel

import android.telephony.PhoneNumberUtils
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultEventDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.EventDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.foundation.input.PhoneFormatTransformation.PHONE_NUMBER_PREFIX
import com.epmedu.animeal.signup.enterphone.domain.SavePhoneNumberUseCase
import com.epmedu.animeal.signup.enterphone.domain.SignUpAndSignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class EnterPhoneViewModel @Inject constructor(
    private val signUpAndSignInUseCase: SignUpAndSignInUseCase,
    private val savePhoneNumberUseCase: SavePhoneNumberUseCase,
) : ViewModel(),
    StateDelegate<EnterPhoneState> by DefaultStateDelegate(initialState = EnterPhoneState()),
    EventDelegate<EnterPhoneViewModel.Event> by DefaultEventDelegate() {

    fun updatePhoneNumber(newNumber: String) {
        updateState {
            copy(
                phoneNumber = newNumber,
                isNextEnabled = newNumber.isValidPhoneNumber()
            )
        }
    }

    private fun String.isValidPhoneNumber(): Boolean {
        return length == PHONE_NUMBER_LENGTH && PhoneNumberUtils.isGlobalPhoneNumber(this)
    }

    fun savePhoneNumberAndSendCode() {
        val phoneNumber = PHONE_NUMBER_PREFIX + state.phoneNumber

        viewModelScope.launch {
            signUpAndSignInUseCase(
                phoneNumber,
                AMPLIFY_PASSWORD,
                {
                    savePhoneNumberAndNavigateNext(phoneNumber)
                },
                {
                    updateNextEnabled()
                    updateError(true)
                }
            )
        }
    }

    private fun savePhoneNumberAndNavigateNext(phoneNumber: String) {
        viewModelScope.launch {
            savePhoneNumberUseCase(
                phoneNumber = phoneNumber,
                onSuccess = {
                    updateError(false)
                    navigateToEnterCode()
                },
                onError = {
                    updateError(true)
                }
            )
        }
    }

    private fun navigateToEnterCode() {
        viewModelScope.launch {
            sendEvent(Event.NavigateToEnterCode)
        }
    }

    private fun updateNextEnabled(isNextEnabled: Boolean? = null) {
        updateState {
            copy(
                isNextEnabled = isNextEnabled ?: state.phoneNumber.isValidPhoneNumber()
            )
        }
    }

    private fun updateError(isError: Boolean) {
        viewModelScope.launch {
            updateState {
                copy(
                    isError = isError
                )
            }
        }
    }

    sealed interface Event {
        object NavigateToEnterCode : Event
    }

    companion object {
        private const val PHONE_NUMBER_LENGTH = 9
        private const val AMPLIFY_PASSWORD = "Password1)"
    }
}