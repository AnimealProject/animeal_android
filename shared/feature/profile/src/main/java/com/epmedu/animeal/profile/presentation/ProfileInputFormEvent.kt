package com.epmedu.animeal.profile.presentation

import com.epmedu.animeal.profile.domain.model.Region

sealed interface ProfileInputFormEvent {
    data class NameChanged(val name: String) : ProfileInputFormEvent
    data class SurnameChanged(val surname: String) : ProfileInputFormEvent
    data class EmailChanged(val email: String) : ProfileInputFormEvent
    data class PhoneNumberChanged(val phoneNumber: String) : ProfileInputFormEvent
    data class RegionChanged(val region: Region) : ProfileInputFormEvent
    data class AgeConfirmationChanged(val isConfirmed: Boolean) : ProfileInputFormEvent
    data object NameFocusCleared : ProfileInputFormEvent
    data object SurnameFocusCleared : ProfileInputFormEvent
    data object EmailFocusCleared : ProfileInputFormEvent
    data object PhoneNumberFocusCleared : ProfileInputFormEvent
}