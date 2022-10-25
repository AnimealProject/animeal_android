package com.epmedu.animeal.tabs.more.account.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amplifyframework.core.Amplify
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultEventDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.EventDelegate
import com.epmedu.animeal.profile.data.repository.ProfileRepository
import com.epmedu.animeal.tabs.more.account.viewmodel.AccountViewModel.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class AccountViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : ViewModel(),
    EventDelegate<Event> by DefaultEventDelegate() {

    internal fun logout() {
        Amplify.Auth.signOut(
            {
                viewModelScope.launch {
                    profileRepository.clearProfile()
                    sendEvent(Event.NavigateToOnboarding)
                }
            },
            { exception ->
                Log.e("SignOut exception", "$exception")
            }
        )
    }

    sealed interface Event {
        object NavigateToOnboarding : Event
    }
}