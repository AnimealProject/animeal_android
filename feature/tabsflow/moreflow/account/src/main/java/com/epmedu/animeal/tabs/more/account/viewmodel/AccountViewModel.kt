package com.epmedu.animeal.tabs.more.account.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epmedu.animeal.common.presentation.viewmodel.delegate.DefaultEventDelegate
import com.epmedu.animeal.common.presentation.viewmodel.delegate.EventDelegate
import com.epmedu.animeal.profile.domain.LogOutUseCase
import com.epmedu.animeal.tabs.more.account.AccountEvent
import com.epmedu.animeal.tabs.more.account.AccountEvent.NavigateToOnboarding
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class AccountViewModel @Inject constructor(
    private val logOutUseCase: LogOutUseCase,
) : ViewModel(),
    EventDelegate<AccountEvent> by DefaultEventDelegate() {

    internal fun logout() {
        viewModelScope.launch {
            logOutUseCase(
                onSuccess = {
                    viewModelScope.launch { sendEvent(NavigateToOnboarding) }
                },
                onError = {
                    // TODO clarify how to handle it
                }
            )
        }
    }
}