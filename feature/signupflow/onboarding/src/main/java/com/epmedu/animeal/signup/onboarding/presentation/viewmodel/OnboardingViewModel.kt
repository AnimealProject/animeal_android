package com.epmedu.animeal.signup.onboarding.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.auth.AuthenticationType
import com.epmedu.animeal.common.presentation.viewmodel.delegate.ActionDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.handler.loading.LoadingHandler
import com.epmedu.animeal.networkuser.domain.usecase.authenticationtype.SetMobileAuthenticationTypeUseCase
import com.epmedu.animeal.profile.data.model.Profile
import com.epmedu.animeal.profile.domain.SaveProfileUseCase
import com.epmedu.animeal.signup.onboarding.presentation.OnboardingScreenEvent
import com.epmedu.animeal.signup.onboarding.presentation.OnboardingScreenEvent.ErrorShown
import com.epmedu.animeal.signup.onboarding.presentation.OnboardingScreenEvent.SignInFinished
import com.epmedu.animeal.signup.onboarding.presentation.OnboardingScreenEvent.SignInWithMobileClicked
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class OnboardingViewModel @Inject constructor(
    actionDelegate: ActionDelegate,
    private val setMobileAuthenticationTypeUseCase: SetMobileAuthenticationTypeUseCase,
    private val saveProfileUseCase: SaveProfileUseCase,
    private val loadingHandler: LoadingHandler
) : ViewModel(),
    StateDelegate<OnboardingState> by DefaultStateDelegate(initialState = OnboardingState()),
    ActionDelegate by actionDelegate {

    fun handleEvent(event: OnboardingScreenEvent) {
        when (event) {
            SignInWithMobileClicked -> changeAuthenticationTypeToMobile()
            ErrorShown -> updateState { copy(isError = false) }
            SignInFinished -> updateState { copy(authenticationType = null) }
        }
    }

    private fun changeAuthenticationTypeToMobile() {
        loadingHandler.showLoading()
        viewModelScope.launch {
            setMobileAuthenticationTypeUseCase()
            updateState { copy(authenticationType = AuthenticationType.Mobile) }
        }
    }

    private fun showError() {
        loadingHandler.hideLoading()
        updateState { copy(isError = true) }
    }

    private fun saveLocalProfile(profile: Profile) {
        viewModelScope.launch { saveProfileUseCase(profile) }
    }
}
