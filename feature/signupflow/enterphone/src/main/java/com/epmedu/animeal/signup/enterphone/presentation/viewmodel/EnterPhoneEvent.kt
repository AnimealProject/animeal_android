package com.epmedu.animeal.signup.enterphone.presentation.viewmodel

sealed interface EnterPhoneEvent {
    data object NavigateToEnterCode : EnterPhoneEvent
}