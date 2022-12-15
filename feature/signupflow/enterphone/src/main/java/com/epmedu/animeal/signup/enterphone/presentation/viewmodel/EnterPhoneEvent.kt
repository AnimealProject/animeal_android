package com.epmedu.animeal.signup.enterphone.presentation.viewmodel

sealed interface EnterPhoneEvent {
    object NavigateToEnterCode : EnterPhoneEvent
}