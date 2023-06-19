package com.epmedu.animeal.signup.enterphone.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.component.BuildConfigProvider
import com.epmedu.animeal.common.constants.DefaultConstants
import com.epmedu.animeal.common.domain.wrapper.ActionResult
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultEventDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.EventDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.foundation.common.validation.result.PhoneNumberValidationResult
import com.epmedu.animeal.foundation.common.validation.validator.ProfileValidator
import com.epmedu.animeal.profile.domain.model.Region
import com.epmedu.animeal.signup.enterphone.domain.SavePhoneNumberInfoUseCase
import com.epmedu.animeal.signup.enterphone.domain.SignUpAndSignInUseCase
import com.epmedu.animeal.signup.enterphone.presentation.EnterPhoneScreenEvent
import com.epmedu.animeal.signup.enterphone.presentation.EnterPhoneScreenEvent.NextButtonClicked
import com.epmedu.animeal.signup.enterphone.presentation.EnterPhoneScreenEvent.UpdatePhoneNumber
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class EnterPhoneViewModel @Inject constructor(
    private val signUpAndSignInUseCase: SignUpAndSignInUseCase,
    private val savePhoneNumberInfoUseCase: SavePhoneNumberInfoUseCase,
    private val validator: ProfileValidator,
    private val buildConfigProvider: BuildConfigProvider,
) : ViewModel(),
    StateDelegate<EnterPhoneState> by DefaultStateDelegate(
        initialState = EnterPhoneState(isDebug = buildConfigProvider.isDebug)
    ),
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
                isNextEnabled = newNumber.isValidPhoneNumber(this.region.phoneNumberDigitsCount)
            )
        }
    }

    private fun String.isValidPhoneNumber(phoneNumberDigitsCount: IntArray): Boolean {
        return validator.validatePhoneNumber(
            this,
            phoneNumberDigitsCount
        ) == PhoneNumberValidationResult.ValidPhoneNumber
    }

    private fun sendCodeAndSavePhoneNumberAndPrefix() {
        val phoneNumber = state.prefix + state.phoneNumber

        viewModelScope.launch {
            when (signUpAndSignInUseCase(phoneNumber, AMPLIFY_PASSWORD)) {
                is ActionResult.Success -> {
                    savePhoneNumberAndPrefixAndNavigateNext()
                }

                is ActionResult.Failure -> {
                    updateNextEnabled()
                    updateError(true)
                }
            }
        }
    }

    private fun savePhoneNumberAndPrefixAndNavigateNext() {
        viewModelScope.launch {
            savePhoneNumberInfoUseCase(
                region = state.region,
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
                    ?: state.phoneNumber.isValidPhoneNumber(region.phoneNumberDigitsCount)
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