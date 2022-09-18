package com.epmedu.animeal.signup.finishprofile.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultEventDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.EventDelegate
import com.epmedu.animeal.navigation.route.AuthenticationType
import com.epmedu.animeal.profile.domain.GetProfileUseCase
import com.epmedu.animeal.profile.domain.LogOutUseCase
import com.epmedu.animeal.profile.domain.SaveProfileUseCase
import com.epmedu.animeal.profile.domain.ValidateBirthDateUseCase
import com.epmedu.animeal.profile.domain.ValidateEmailUseCase
import com.epmedu.animeal.profile.domain.ValidateNameUseCase
import com.epmedu.animeal.profile.domain.ValidatePhoneNumberUseCase
import com.epmedu.animeal.profile.domain.ValidateSurnameUseCase
import com.epmedu.animeal.profile.domain.network.DeleteNetworkUserUseCase
import com.epmedu.animeal.profile.domain.network.UpdateNetworkProfileUseCase
import com.epmedu.animeal.profile.presentation.ProfileInputFormEvent.PhoneNumberChanged
import com.epmedu.animeal.profile.presentation.viewmodel.BaseProfileViewModel
import com.epmedu.animeal.profile.presentation.viewmodel.ProfileState.FormState.EDITABLE
import com.epmedu.animeal.signup.finishprofile.presentation.FinishProfileScreenEvent
import com.epmedu.animeal.signup.finishprofile.presentation.FinishProfileScreenEvent.Cancel
import com.epmedu.animeal.signup.finishprofile.presentation.FinishProfileScreenEvent.Submit
import com.epmedu.animeal.signup.finishprofile.presentation.viewmodel.FinishProfileEvent.NavigateBack
import com.epmedu.animeal.signup.finishprofile.presentation.viewmodel.FinishProfileEvent.Saved
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("LongParameterList")
@HiltViewModel
internal class FinishProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val saveProfileUseCase: SaveProfileUseCase,
    private val updateNetworkProfileUseCase: UpdateNetworkProfileUseCase,
    private val deleteNetworkUserUseCase: DeleteNetworkUserUseCase,
    private val validateNameUseCase: ValidateNameUseCase,
    private val validateSurnameUseCase: ValidateSurnameUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePhoneNumberUseCase: ValidatePhoneNumberUseCase,
    private val validateBirthDateUseCase: ValidateBirthDateUseCase,
    private val logOutUseCase: LogOutUseCase
) : BaseProfileViewModel(
    validateNameUseCase,
    validateSurnameUseCase,
    validateEmailUseCase,
    validateBirthDateUseCase
),
    EventDelegate<FinishProfileEvent> by DefaultEventDelegate() {

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            getProfileUseCase().collect {
                updateState {
                    copy(profile = it, formState = EDITABLE)
                }
            }
        }
    }

    fun enablePhoneInput() {
        updateState { copy(isPhoneNumberEnabled = true) }
    }

    override fun handlePhoneNumberChangedEvent(event: PhoneNumberChanged) {
        updateState {
            copy(
                profile = profile.copy(phoneNumber = event.phoneNumber),
                phoneNumberError = validatePhoneNumberUseCase(event.phoneNumber)
            )
        }
    }

    fun handleScreenEvents(
        event: FinishProfileScreenEvent,
        authenticationType: AuthenticationType
    ) {
        when (event) {
            Submit -> submitProfile()
            Cancel -> {
                when (authenticationType) {
                    AuthenticationType.Mobile -> logout()
                    AuthenticationType.Facebook -> removeUnfinishedNetworkUser()
                }
            }
        }
    }

    private fun submitProfile() {
        validateProfile()
        if (state.hasErrors().not()) {
            updateState {
                copy(
                    profile = profile.copy(phoneNumber = profile.phoneNumber),
                )
            }
            saveProfile()
        }
    }

    private fun logout() {
        viewModelScope.launch {
            logOutUseCase(
                onSuccess = { navigateBack() },
                onError = {}
            )
        }
    }

    private fun removeUnfinishedNetworkUser() {
        viewModelScope.launch {
            deleteNetworkUserUseCase(
                onSuccess = { navigateBack() },
                onError = {}
            )
        }
    }

    private fun navigateBack() {
        viewModelScope.launch {
            sendEvent(NavigateBack)
        }
    }

    private fun validateProfile() {
        updateState {
            with(profile) {
                copy(
                    nameError = validateNameUseCase(name),
                    surnameError = validateSurnameUseCase(surname),
                    emailError = validateEmailUseCase(email),
                    phoneNumberError = validatePhoneNumberUseCase(phoneNumber),
                    birthDateError = validateBirthDateUseCase(birthDate)
                )
            }
        }
    }

    private fun saveProfile() {
        viewModelScope.launch {
            saveProfileUseCase(state.profile).collect {
                updateNetworkProfileUseCase.invoke(
                    profile = state.profile,
                    onSuccess = {},
                    onError = {}
                )
                sendEvent(Saved)
            }
        }
    }
}