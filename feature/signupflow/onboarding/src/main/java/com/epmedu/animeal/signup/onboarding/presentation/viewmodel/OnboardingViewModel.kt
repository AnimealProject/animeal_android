package com.epmedu.animeal.signup.onboarding.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.auth.AuthenticationType
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.networkuser.data.mapper.AuthUserAttributesToIsPhoneVerifiedMapper
import com.epmedu.animeal.networkuser.data.mapper.AuthUserAttributesToProfileMapper
import com.epmedu.animeal.networkuser.domain.usecase.FetchNetworkUserAttributesUseCase
import com.epmedu.animeal.networkuser.domain.usecase.authenticationtype.SetFacebookAuthenticationTypeUseCase
import com.epmedu.animeal.networkuser.domain.usecase.authenticationtype.SetMobileAuthenticationTypeUseCase
import com.epmedu.animeal.profile.data.model.Profile
import com.epmedu.animeal.profile.domain.SaveProfileUseCase
import com.epmedu.animeal.signup.onboarding.presentation.OnboardingScreenEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class OnboardingViewModel @Inject constructor(
    private val authUserAttributesToProfileMapper: AuthUserAttributesToProfileMapper,
    private val authUserAttributesToIsPhoneVerifiedMapper: AuthUserAttributesToIsPhoneVerifiedMapper,
    private val fetchNetworkUserAttributesUseCase: FetchNetworkUserAttributesUseCase,
    private val setMobileAuthenticationTypeUseCase: SetMobileAuthenticationTypeUseCase,
    private val setFacebookAuthenticationTypeUseCase: SetFacebookAuthenticationTypeUseCase,
    private val saveProfileUseCase: SaveProfileUseCase,
) : ViewModel(),
    StateDelegate<OnboardingState> by DefaultStateDelegate(initialState = OnboardingState()) {

    fun handleEvent(event: OnboardingScreenEvent) {
        when (event) {
            OnboardingScreenEvent.RedirectedFromFacebookWebUi -> {
                loadNetworkUserAttributesIntoLocalProfile()
            }
            OnboardingScreenEvent.SignInWithMobileClicked -> {
                changeAuthenticationTypeToMobile()
            }
            OnboardingScreenEvent.SignInFinished -> {
                updateState {
                    copy(authenticationType = null)
                }
            }
        }
    }

    private fun changeAuthenticationTypeToMobile() {
        viewModelScope.launch {
            setMobileAuthenticationTypeUseCase.invoke()
            updateState {
                copy(
                    authenticationType = AuthenticationType.Mobile
                )
            }
        }
    }

    private fun changeAuthenticationTypeToFacebook(isPhoneNumberVerified: Boolean) {
        viewModelScope.launch {
            setFacebookAuthenticationTypeUseCase.invoke(isPhoneNumberVerified)
        }
    }

    private fun loadNetworkUserAttributesIntoLocalProfile() {
        viewModelScope.launch {
            fetchNetworkUserAttributesUseCase(
                onSuccess = { networkUserAttributes ->
                    val isPhoneNumberVerified =
                        authUserAttributesToIsPhoneVerifiedMapper.map(networkUserAttributes)
                    changeAuthenticationTypeToFacebook(isPhoneNumberVerified)
                    val profile = authUserAttributesToProfileMapper.map(networkUserAttributes)
                    saveLocalProfile(profile, isPhoneNumberVerified)
                },
                onError = {
                    // TODO discuss how to handle
                }
            )
        }
    }

    private fun saveLocalProfile(
        profile: Profile,
        isPhoneNumberVerified: Boolean
    ) {
        viewModelScope.launch {
            saveProfileUseCase(profile).collect {
                updateState {
                    copy(
                        authenticationType = AuthenticationType.Facebook(isPhoneNumberVerified)
                    )
                }
            }
        }
    }
}
