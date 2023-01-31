package com.epmedu.animeal.signup.enterphone.presentation.viewmodel

import android.telephony.PhoneNumberUtils
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.constants.DefaultConstants
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultEventDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.EventDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.signup.enterphone.domain.SavePhoneNumberAndPrefixUseCase
import com.epmedu.animeal.signup.enterphone.domain.SignUpAndSignInUseCase
import com.epmedu.animeal.signup.enterphone.presentation.EnterPhoneScreenEvent
import com.epmedu.animeal.signup.enterphone.presentation.EnterPhoneScreenEvent.NextButtonClicked
import com.epmedu.animeal.signup.enterphone.presentation.EnterPhoneScreenEvent.UpdatePhoneNumber
import com.epmedu.animeal.signup.enterphone.presentation.Region
import com.epmedu.animeal.signup.enterphone.presentation.phoneNumberDigitsCount
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class EnterPhoneViewModel @Inject constructor(
    private val signUpAndSignInUseCase: SignUpAndSignInUseCase,
    private val savePhoneNumberAndPrefixUseCase: SavePhoneNumberAndPrefixUseCase,
) : ViewModel(),
    StateDelegate<EnterPhoneState> by DefaultStateDelegate(initialState = EnterPhoneState()),
    EventDelegate<EnterPhoneEvent> by DefaultEventDelegate() {

    fun handleEvents(event: EnterPhoneScreenEvent) {
        when (event) {
            is UpdatePhoneNumber -> updatePhoneNumber(event.phoneNumber)
            is NextButtonClicked -> sendCodeAndSavePhoneNumberAndPrefix()
            is EnterPhoneScreenEvent.RegionChosen -> updateRegion(event.region)
        }
    }

    private fun updateRegion(region: Region) {
        updateState { copy(region = region, phoneNumber = DefaultConstants.EMPTY_STRING) }
    }

    private fun updatePhoneNumber(newNumber: String) {
        updateState {
            copy(
                phoneNumber = newNumber,
                isNextEnabled = newNumber.isValidPhoneNumber(this.region.phoneNumberDigitsCount())
            )
        }
    }

    private fun String.isValidPhoneNumber(phoneNumberDigitsCount: IntArray): Boolean {
        return phoneNumberDigitsCount.contains(length) && PhoneNumberUtils.isGlobalPhoneNumber(this)
    }

    private fun sendCodeAndSavePhoneNumberAndPrefix() {
        val phoneNumber = state.prefix + state.phoneNumber

        viewModelScope.launch {
            signUpAndSignInUseCase(
                phoneNumber,
                AMPLIFY_PASSWORD,
                {
                    savePhoneNumberAndPrefixAndNavigateNext()
                },
                {
                    updateNextEnabled()
                    updateError(true)
                }
            )
        }
    }

    private fun savePhoneNumberAndPrefixAndNavigateNext() {
        viewModelScope.launch {
            savePhoneNumberAndPrefixUseCase(
                prefix = state.prefix,
                phoneNumber = state.phoneNumber,
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
            sendEvent(EnterPhoneEvent.NavigateToEnterCode)
        }
    }

    private fun updateNextEnabled(isNextEnabled: Boolean? = null) {
        updateState {
            copy(
                isNextEnabled = isNextEnabled
                    ?: state.phoneNumber.isValidPhoneNumber(region.phoneNumberDigitsCount())
            )
        }
    }

    private fun updateError(isError: Boolean) {
        updateState { copy(isError = isError) }
    }

    companion object {
        private const val AMPLIFY_PASSWORD = "Password1)"
    }
}