package com.epmedu.animeal.signup.enterphone.presentation

sealed interface EnterPhoneScreenEvent {
    data class UpdatePhoneNumber(val phoneNumber: String) : EnterPhoneScreenEvent
    object NextButtonClicked : EnterPhoneScreenEvent
}