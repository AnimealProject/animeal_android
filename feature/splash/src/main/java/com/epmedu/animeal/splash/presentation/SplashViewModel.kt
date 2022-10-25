package com.epmedu.animeal.splash.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amplifyframework.core.Amplify
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultEventDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.EventDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel(),
    EventDelegate<SplashViewModel.Event> by DefaultEventDelegate() {

    fun verifyProfileSaved() {
        Amplify.Auth.fetchAuthSession(
            { session ->
                if (session.isSignedIn) {
                    navigateToHome()
                } else {
                    navigateToOnboarding()
                }
            },
            {
                navigateToOnboarding()
            }
        )
    }

    private fun navigateToHome() {
        viewModelScope.launch {
            sendEvent(Event.NavigateToHome)
        }
    }

    private fun navigateToOnboarding() {
        viewModelScope.launch {
            sendEvent(Event.NavigateToOnboarding)
        }
    }

    sealed interface Event {
        object NavigateToHome : Event
        object NavigateToOnboarding : Event
    }
}