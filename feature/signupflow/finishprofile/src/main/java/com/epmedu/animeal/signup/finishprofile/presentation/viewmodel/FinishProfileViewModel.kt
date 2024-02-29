package com.epmedu.animeal.signup.finishprofile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.auth.AuthenticationType
import com.epmedu.animeal.common.component.BuildConfigProvider
import com.epmedu.animeal.common.presentation.viewmodel.delegate.ActionDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
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
import com.epmedu.animeal.profile.presentation.viewmodel.ProfileInputFormState.FormState.EDITABLE
import com.epmedu.animeal.profile.presentation.viewmodel.handler.ProfileInputFormHandler
import com.epmedu.animeal.signup.finishprofile.presentation.FinishProfileScreenEvent
import com.epmedu.animeal.signup.finishprofile.presentation.FinishProfileScreenEvent.Cancel
import com.epmedu.animeal.signup.finishprofile.presentation.FinishProfileScreenEvent.InputFormEvent
import com.epmedu.animeal.signup.finishprofile.presentation.FinishProfileScreenEvent.NavigatedToNextDestination
import com.epmedu.animeal.signup.finishprofile.presentation.FinishProfileScreenEvent.ScreenDisplayed
import com.epmedu.animeal.signup.finishprofile.presentation.FinishProfileScreenEvent.Submit
import com.epmedu.animeal.signup.finishprofile.presentation.viewmodel.FinishProfileNextDestination.EnterCode
import com.epmedu.animeal.signup.finishprofile.presentation.viewmodel.FinishProfileNextDestination.Onboarding
import com.epmedu.animeal.signup.finishprofile.presentation.viewmodel.FinishProfileNextDestination.Tabs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class FinishProfileViewModel @Inject constructor(
    private val buildConfigProvider: BuildConfigProvider,
    private val actionDelegate: ActionDelegate,
    private val profileInputFormHandler: ProfileInputFormHandler,
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
) : ViewModel(),
    StateDelegate<FinishProfileState> by DefaultStateDelegate(FinishProfileState()),
    ActionDelegate by actionDelegate {

    private var authenticationType: AuthenticationType = AuthenticationType.Mobile

    init {
        loadProfile()
        loadAuthenticationType()
        collectProfileInputFormState()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            getProfileUseCase().collect {
                profileInputFormHandler.updateState {
                    copy(profile = it, formState = EDITABLE)
                }
            }
        }
    }

    private fun loadAuthenticationType() {
        viewModelScope.launch {
            authenticationType = getAuthenticationTypeUseCase()
            if (authenticationType is AuthenticationType.Facebook) {
                profileInputFormHandler.updateState {
                    copy(
                        isCountrySelectorClickable = buildConfigProvider.isProdFlavor.not(),
                        isPhoneNumberEnabled = true
                    )
                }
            }
        }
    }

    private fun collectProfileInputFormState() {
        viewModelScope.launch {
            profileInputFormHandler.stateFlow.collect { inputFormState ->
                val errors = with(inputFormState.profile) {
                    setOf(
                        validateNameUseCase(name),
                        validateSurnameUseCase(surname),
                        validateEmailUseCase(email),
                        validateBirthDateUseCase(birthDate),
                        validatePhoneNumberUseCase(phoneNumber, phoneNumberRegion.phoneNumberDigitsCount)
                    )
                }
                updateState {
                    copy(
                        profileInputFormState = inputFormState,
                        isDoneButtonEnabled = errors.all { it is UiText.Empty }
                    )
                }
            }
        }
    }

    fun handleScreenEvents(
        event: FinishProfileScreenEvent,
    ) {
        when (event) {
            is InputFormEvent -> {
                profileInputFormHandler.handleInputFormEvent(event.event)
            }

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

            NavigatedToNextDestination -> {
                updateState { copy(nextDestination = null) }
            }
        }
    }

    private fun submitProfile() {
        if (state.profileInputFormState.hasErrors().not()) {
            profileInputFormHandler.updateState {
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
            updateState { copy(nextDestination = Onboarding) }
        }
    }

    private fun saveProfile() {
        viewModelScope.launch {
            saveProfileUseCase(state.profileInputFormState.profile).collect {
                performAction(
                    action = { updateNetworkProfileUseCase(state.profileInputFormState.profile) },
                    onSuccess = ::navigateToNextDestination,
                    onError = loadingHandler::hideLoading
                )
            }
        }
    }

    private fun navigateToNextDestination() {
        updateState {
            copy(
                nextDestination = when (authenticationType) {
                    AuthenticationType.Mobile -> Tabs
                    is AuthenticationType.Facebook -> EnterCode
                }
            )
        }
    }
}