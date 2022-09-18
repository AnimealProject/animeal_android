package com.epmedu.animeal.signup.onboarding.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultEventDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.EventDelegate
import com.epmedu.animeal.extensions.DAY_MONTH_NAME_COMMA_YEAR_FORMATTER
import com.epmedu.animeal.extensions.MONTH_DAY_YEAR_SLASH_FORMATTER
import com.epmedu.animeal.extensions.reformatDateToString
import com.epmedu.animeal.profile.data.mapper.AuthUserAttributesToIsPhoneVerifiedMapper
import com.epmedu.animeal.profile.data.mapper.AuthUserAttributesToProfileMapper
import com.epmedu.animeal.profile.domain.SaveProfileUseCase
import com.epmedu.animeal.profile.domain.network.FetchNetworkUserAttributesUseCase
import com.epmedu.animeal.signup.onboarding.presentation.OnboardingViewModel.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class OnboardingViewModel @Inject constructor(
    private val authUserAttributesToProfileMapper: AuthUserAttributesToProfileMapper,
    private val authUserAttributesToIsPhoneVerifiedMapper: AuthUserAttributesToIsPhoneVerifiedMapper,
    private val fetchNetworkUserAttributesUseCase: FetchNetworkUserAttributesUseCase,
    private val saveProfileUseCase: SaveProfileUseCase,
) : ViewModel(),
    EventDelegate<Event> by DefaultEventDelegate() {

    fun loadNetworkProfile() {
        viewModelScope.launch {
            loadNetworkUserAttributesIntoLocalDatastoreProfile()
        }
    }

    private suspend fun loadNetworkUserAttributesIntoLocalDatastoreProfile() {
        fetchNetworkUserAttributesUseCase(
            onSuccess = { networkUserAttributes ->
                val isPhoneNumberVerified = authUserAttributesToIsPhoneVerifiedMapper.map(networkUserAttributes)
                var profile = authUserAttributesToProfileMapper.map(networkUserAttributes)
                val convertedLocalFormatDate = reformatDateToString(
                    dateString = profile.birthDate,
                    originalFormatter = MONTH_DAY_YEAR_SLASH_FORMATTER,
                    newFormatter = DAY_MONTH_NAME_COMMA_YEAR_FORMATTER
                )
                profile = profile.copy(birthDate = convertedLocalFormatDate)
                viewModelScope.launch {
                    saveProfileUseCase(profile).collect {}
                    if (isPhoneNumberVerified) {
                        sendEvent(Event.NavigateToHomeScreen)
                    } else {
                        sendEvent(Event.NavigateToFinishPageInFacebookAuthFlow)
                    }
                }
            },
            onError = {
                // TODO discuss how to handle
            }
        )
    }

    sealed interface Event {
        object NavigateToFinishPageInFacebookAuthFlow : Event
        object NavigateToHomeScreen : Event
    }
}
