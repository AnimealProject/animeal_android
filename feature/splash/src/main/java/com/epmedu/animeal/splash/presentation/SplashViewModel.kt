package com.epmedu.animeal.splash.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.data.repository.ProfileRepository
import com.epmedu.animeal.common.presentation.event.EventSource
import com.epmedu.animeal.common.presentation.event.EventSourceImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : ViewModel(),
    EventSource<SplashViewModel.Event> by EventSourceImpl() {

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