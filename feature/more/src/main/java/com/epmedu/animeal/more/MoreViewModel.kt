package com.epmedu.animeal.more

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.data.repository.ProfileRepository
import com.epmedu.animeal.common.presentation.event.EventSource
import com.epmedu.animeal.common.presentation.event.EventSourceImpl
import com.epmedu.animeal.more.MoreViewModel.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class MoreViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : ViewModel(),
    EventSource<Event> by EventSourceImpl() {

    internal fun logout() {
        viewModelScope.launch {
            profileRepository.clearProfile()
            sendEvent(Event.NavigateToOnboarding)
        }
    }

    sealed interface Event {
        object NavigateToOnboarding : Event
    }
}