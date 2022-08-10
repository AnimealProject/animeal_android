package com.epmedu.animeal.splash.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.data.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val _event = MutableSharedFlow<Event>()
    val event: SharedFlow<Event> get() = _event.asSharedFlow()

    init {
        viewModelScope.launch {
            delay(2000)
            if (profileRepository.isProfileSaved()) {
                _event.emit(Event.NavigateToHome)
            } else {
                _event.emit(Event.NavigateToOnboarding)
            }
        }
    }

    sealed interface Event {
        object NavigateToHome : Event
        object NavigateToOnboarding : Event
    }
}