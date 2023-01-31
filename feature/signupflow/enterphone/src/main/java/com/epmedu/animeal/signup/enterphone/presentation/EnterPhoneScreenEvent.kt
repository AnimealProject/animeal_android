package com.epmedu.animeal.signup.enterphone.presentation

sealed interface EnterPhoneScreenEvent {
    data class UpdatePhoneNumber(val phoneNumber: String) : EnterPhoneScreenEvent
    data class RegionChosen(val region: Region) : EnterPhoneScreenEvent
    object NextButtonClicked : EnterPhoneScreenEvent
}