package com.epmedu.animeal.more

import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.data.repository.ProfileRepository
import com.epmedu.animeal.common.domain.StateViewModel
import com.epmedu.animeal.more.MoreViewModel.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class MoreViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : StateViewModel<Unit, Event>(Unit) {

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