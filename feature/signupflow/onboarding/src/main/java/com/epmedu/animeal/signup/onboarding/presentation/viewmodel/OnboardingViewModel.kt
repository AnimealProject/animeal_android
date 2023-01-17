package com.epmedu.animeal.signup.onboarding.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultStateDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.StateDelegate
import com.epmedu.animeal.extensions.DAY_MONTH_NAME_COMMA_YEAR_FORMATTER
import com.epmedu.animeal.extensions.MONTH_DAY_YEAR_SLASH_FORMATTER
import com.epmedu.animeal.extensions.reformatDateToString
import com.epmedu.animeal.networkuser.data.mapper.AuthUserAttributesToIsPhoneVerifiedMapper
import com.epmedu.animeal.networkuser.data.mapper.AuthUserAttributesToProfileMapper
import com.epmedu.animeal.networkuser.domain.FetchNetworkUserAttributesUseCase
import com.epmedu.animeal.networkuser.domain.authenticationtype.SetFacebookAuthenticationTypeUseCase
import com.epmedu.animeal.networkuser.domain.authenticationtype.SetMobileAuthenticationTypeUseCase
import com.epmedu.animeal.profile.domain.SaveProfileUseCase
import com.epmedu.animeal.signup.onboarding.domain.FacebookAuthorization
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
                changeAuthenticationTypeToFacebook()
                loadNetworkUserAttributesIntoLocalDatastoreProfile()
            }
            OnboardingScreenEvent.SignInWithMobileClicked -> {
                changeAuthenticationTypeToMobile()
            }
            OnboardingScreenEvent.FacebookSignInFinished -> {
                updateState {
                    copy(facebookAuthorization = null)
                }
            }
        }
    }

    private fun changeAuthenticationTypeToMobile() {
        viewModelScope.launch {
            setMobileAuthenticationTypeUseCase.invoke()
        }
    }

    private fun changeAuthenticationTypeToFacebook() {
        viewModelScope.launch {
            setFacebookAuthenticationTypeUseCase.invoke()
        }
    }

    private fun loadNetworkUserAttributesIntoLocalDatastoreProfile() {
        viewModelScope.launch {
            fetchNetworkUserAttributesUseCase(
                onSuccess = { networkUserAttributes ->
                    val isPhoneNumberVerified =
                        authUserAttributesToIsPhoneVerifiedMapper.map(networkUserAttributes)
                    var profile = authUserAttributesToProfileMapper.map(networkUserAttributes)
                    val convertedLocalFormatDate = reformatDateToString(
                        dateString = profile.birthDate,
                        originalFormatter = MONTH_DAY_YEAR_SLASH_FORMATTER,
                        newFormatter = DAY_MONTH_NAME_COMMA_YEAR_FORMATTER
                    )
                    profile = profile.copy(birthDate = convertedLocalFormatDate)
                    viewModelScope.launch {
                        saveProfileUseCase(profile).collect {
                            updateState {
                                copy(
                                    facebookAuthorization =
                                    FacebookAuthorization(isPhoneNumberVerified)
                                )
                            }
                        }
                    }
                },
                onError = {
                    // TODO discuss how to handle
                }
            )
        }
    }
}
