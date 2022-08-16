package com.epmedu.animeal.splash.presentation

import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.data.repository.ProfileRepository
import com.epmedu.animeal.common.domain.StateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : StateViewModel<Unit, SplashViewModel.Event>(Unit) {

    fun verifyProfileSaved() {
        viewModelScope.launch {
            if (profileRepository.isProfileSaved()) {
                sendEvent(Event.NavigateToHome)
            } else {
                sendEvent(Event.NavigateToOnboarding)
            }
        }
    }

    sealed interface Event {
        object NavigateToHome : Event
        object NavigateToOnboarding : Event
    }
}