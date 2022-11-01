package com.epmedu.animeal.signup.enterphone.presentation.viewmodel

import android.telephony.PhoneNumberUtils
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultEventDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.EventDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.foundation.input.PhoneFormatTransformation.PHONE_NUMBER_PREFIX
import com.epmedu.animeal.signup.enterphone.data.EnterPhoneRepository
import com.epmedu.animeal.signup.enterphone.domain.SignUpAndSignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class EnterPhoneViewModel @Inject constructor(
    private val repository: EnterPhoneRepository,
    private val signUpAndSignInUseCase: SignUpAndSignInUseCase,
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
            repository.savePhoneNumber(phoneNumber)
            if (repository.isPhoneNumberSaved()) {
                updateError(false)
                sendEvent(Event.NavigateToEnterCode)
            } else {
                updateError(true)
            }
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