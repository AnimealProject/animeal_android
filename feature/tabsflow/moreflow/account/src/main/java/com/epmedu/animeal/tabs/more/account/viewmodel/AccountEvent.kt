package com.epmedu.animeal.tabs.more.account.viewmodel

sealed interface AccountEvent {
    object NavigateToOnboarding : AccountEvent
}