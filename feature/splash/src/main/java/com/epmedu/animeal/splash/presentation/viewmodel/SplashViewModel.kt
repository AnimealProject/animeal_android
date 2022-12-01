package com.epmedu.animeal.splash.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amplifyframework.auth.AuthSession
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultEventDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.EventDelegate
import com.epmedu.animeal.splash.domain.FetchUserSessionUseCase
import com.epmedu.animeal.splash.presentation.viewmodel.SplashEvent.NavigateToHome
import com.epmedu.animeal.splash.presentation.viewmodel.SplashEvent.NavigateToOnboarding
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val fetchUserSessionUseCase: FetchUserSessionUseCase,
) :
    ViewModel(),
    EventDelegate<SplashEvent> by DefaultEventDelegate() {

    fun verifyProfileSaved() {
        viewModelScope.launch {
            fetchUserSessionUseCase(
                ::verifyProfileSuccess
            ) {
                navigateToOnboarding()
            }
        }
    }

    private fun verifyProfileSuccess(result: Any?) {
        if (result is AuthSession) {
            processSession(result)
        } else {
            navigateToOnboarding()
        }
    }

    private fun processSession(session: AuthSession) {
        if (session.isSignedIn) {
            navigateToHome()
        } else {
            navigateToOnboarding()
        }
    }

    private fun navigateToHome() {
        viewModelScope.launch {
            sendEvent(NavigateToHome)
        }
    }

    private fun navigateToOnboarding() {
        viewModelScope.launch {
            sendEvent(NavigateToOnboarding)
        }
    }
}