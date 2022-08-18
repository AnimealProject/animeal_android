package com.epmedu.animeal.more

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.data.repository.ProfileRepository
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultEventDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.EventDelegate
import com.epmedu.animeal.more.MoreViewModel.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class MoreViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : ViewModel(),
    EventDelegate<Event> by DefaultEventDelegate() {

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