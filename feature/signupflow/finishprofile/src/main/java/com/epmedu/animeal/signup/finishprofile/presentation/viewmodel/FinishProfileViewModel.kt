package com.epmedu.animeal.signup.finishprofile.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.auth.AuthenticationType
import com.epmedu.animeal.common.component.BuildConfigProvider
import com.epmedu.animeal.common.presentation.viewmodel.delegate.ActionDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultEventDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.EventDelegate
import com.epmedu.animeal.common.presentation.viewmodel.handler.loading.LoadingHandler
import com.epmedu.animeal.foundation.common.UiText
import com.epmedu.animeal.networkuser.domain.usecase.DeleteNetworkUserUseCase
import com.epmedu.animeal.networkuser.domain.usecase.LogOutUseCase
import com.epmedu.animeal.networkuser.domain.usecase.UpdateNetworkProfileUseCase
import com.epmedu.animeal.networkuser.domain.usecase.authenticationtype.GetAuthenticationTypeUseCase
import com.epmedu.animeal.profile.domain.ClearProfileUseCase
import com.epmedu.animeal.profile.domain.GetProfileUseCase
import com.epmedu.animeal.profile.domain.SaveProfileUseCase
import com.epmedu.animeal.profile.domain.ValidateBirthDateUseCase
import com.epmedu.animeal.profile.domain.ValidateEmailUseCase
import com.epmedu.animeal.profile.domain.ValidateNameUseCase
import com.epmedu.animeal.profile.domain.ValidatePhoneNumberUseCase
import com.epmedu.animeal.profile.domain.ValidateSurnameUseCase
import com.epmedu.animeal.profile.presentation.ProfileInputFormEvent.PhoneNumberChanged
import com.epmedu.animeal.profile.presentation.viewmodel.BaseProfileViewModel
import com.epmedu.animeal.profile.presentation.viewmodel.ProfileState.FormState.EDITABLE
import com.epmedu.animeal.signup.finishprofile.presentation.FinishProfileScreenEvent
import com.epmedu.animeal.signup.finishprofile.presentation.FinishProfileScreenEvent.Cancel
import com.epmedu.animeal.signup.finishprofile.presentation.FinishProfileScreenEvent.ScreenDisplayed
import com.epmedu.animeal.signup.finishprofile.presentation.FinishProfileScreenEvent.Submit
import com.epmedu.animeal.signup.finishprofile.presentation.viewmodel.FinishProfileEvent.NavigateBackToOnboarding
import com.epmedu.animeal.signup.finishprofile.presentation.viewmodel.FinishProfileEvent.NavigateToConfirmPhone
import com.epmedu.animeal.signup.finishprofile.presentation.viewmodel.FinishProfileEvent.ProfileFinished
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class FinishProfileViewModel @Inject constructor(
    private val buildConfigProvider: BuildConfigProvider,
    private val actionDelegate: ActionDelegate,
    private val getProfileUseCase: GetProfileUseCase,
    private val getAuthenticationTypeUseCase: GetAuthenticationTypeUseCase,
    private val saveProfileUseCase: SaveProfileUseCase,
    private val updateNetworkProfileUseCase: UpdateNetworkProfileUseCase,
    private val clearProfileUseCase: ClearProfileUseCase,
    private val deleteNetworkUserUseCase: DeleteNetworkUserUseCase,
    private val validateNameUseCase: ValidateNameUseCase,
    private val validateSurnameUseCase: ValidateSurnameUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePhoneNumberUseCase: ValidatePhoneNumberUseCase,
    private val validateBirthDateUseCase: ValidateBirthDateUseCase,
    private val logOutUseCase: LogOutUseCase,
    private val loadingHandler: LoadingHandler
) : BaseProfileViewModel(
    validateNameUseCase = validateNameUseCase,
    validateSurnameUseCase = validateSurnameUseCase,
    validateEmailUseCase = validateEmailUseCase,
    validatePhoneNumberUseCase = validatePhoneNumberUseCase,
    validateBirthDateUseCase = validateBirthDateUseCase
),
    ActionDelegate by actionDelegate,
    EventDelegate<FinishProfileEvent> by DefaultEventDelegate() {

    private var authenticationType: AuthenticationType = AuthenticationType.Mobile

    init {
        loadProfile()
        loadAuthenticationType()
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

    private fun loadAuthenticationType() {
        viewModelScope.launch {
            authenticationType = getAuthenticationTypeUseCase()
            if (authenticationType is AuthenticationType.Facebook) {
                updateState {
                    copy(
                        isCountrySelectorClickable = buildConfigProvider.isProdFlavor.not(),
                        isPhoneNumberEnabled = true
                    )
                }
            }
        }
    }

    override fun handlePhoneNumberChangedEvent(event: PhoneNumberChanged) {
        updateState {
            copy(
                profile = profile.copy(phoneNumber = event.phoneNumber),
                phoneNumberError = UiText.Empty
            )
        }
    }

    fun handleScreenEvents(
        event: FinishProfileScreenEvent,
    ) {
        when (event) {
            Submit -> {
                submitProfile()
            }

            Cancel -> {
                when (authenticationType) {
                    AuthenticationType.Mobile -> logout()
                    is AuthenticationType.Facebook -> removeUnfinishedNetworkUser()
                }
            }

            ScreenDisplayed -> {
                loadingHandler.hideLoading()
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
            loadingHandler.showLoading()
            saveProfile()
        }
    }

    private fun logout() {
        viewModelScope.launch {
            performAction(
                action = {
                    logOutUseCase()
                },
                onSuccess = {
                    clearProfileUseCase()
                    navigateBack()
                },
                onError = {}
            )
        }
    }

    private fun removeUnfinishedNetworkUser() {
        viewModelScope.launch {
            performAction(
                action = deleteNetworkUserUseCase::invoke,
                onSuccess = {
                    clearProfileUseCase()
                    navigateBack()
                },
                onError = {}
            )
        }
    }

    private fun navigateBack() {
        viewModelScope.launch {
            sendEvent(NavigateBackToOnboarding)
        }
    }

    private fun validateProfile() {
        updateState {
            with(profile) {
                copy(
                    nameError = validateNameUseCase(name),
                    surnameError = validateSurnameUseCase(surname),
                    emailError = validateEmailUseCase(email),
                    phoneNumberError = validatePhoneNumberUseCase(
                        phoneNumber,
                        state.phoneNumberDigitsCount
                    ),
                    birthDateError = validateBirthDateUseCase(birthDate)
                )
            }
        }
    }

    private fun saveProfile() {
        viewModelScope.launch {
            saveProfileUseCase(state.profile).collect {
                performAction(
                    action = { updateNetworkProfileUseCase(state.profile) },
                    onSuccess = {
                        sendEvent(
                            when (authenticationType) {
                                AuthenticationType.Mobile -> ProfileFinished
                                is AuthenticationType.Facebook -> NavigateToConfirmPhone
                            }
                        )
                    },
                    onError = loadingHandler::hideLoading
                )
            }
        }
    }
}