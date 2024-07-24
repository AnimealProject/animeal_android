package com.epmedu.animeal.signup.enterphone.presentation

import com.epmedu.animeal.profile.domain.model.Region

sealed interface EnterPhoneScreenEvent {
    data class UpdatePhoneNumber(val phoneNumber: String) : EnterPhoneScreenEvent
    data class RegionChosen(val region: Region) : EnterPhoneScreenEvent
    data object NextButtonClicked : EnterPhoneScreenEvent
    data object ScreenDisplayed : EnterPhoneScreenEvent
    data object NavigatedToEnterCode : EnterPhoneScreenEvent
}