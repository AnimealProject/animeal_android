package com.epmedu.animeal.tabs.more.account

sealed interface AccountEvent {
    object NavigateToOnboarding : AccountEvent
}