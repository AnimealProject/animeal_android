package com.epmedu.animeal.splash.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amplifyframework.auth.AuthSession
import com.epmedu.animeal.auth.AuthRequestHandler
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultEventDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.EventDelegate
import com.epmedu.animeal.splash.domain.FetchUserSessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val fetchUserSessionUseCase: FetchUserSessionUseCase,
) :
    ViewModel(),
    EventDelegate<SplashViewModel.Event> by DefaultEventDelegate() {

    private val sessionRequestHandler = object : AuthRequestHandler {
        override fun onSuccess(result: Any?) {
            Log.d("sessionRequest", "result -> $result")
            if (result is AuthSession) {
                processSession(result)
            } else {
                navigateToOnboarding()
            }
        }

        override fun onError(exception: Exception) {
            Log.d("sessionRequest", "exception -> $exception")
            navigateToOnboarding()
        }
    }

    fun verifyProfileSaved() {
        viewModelScope.launch {
            fetchUserSessionUseCase(sessionRequestHandler)
        }
    }

    fun processSession(session: AuthSession) {
        if (session.isSignedIn) {
            navigateToHome()
        } else {
            navigateToOnboarding()
        }
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