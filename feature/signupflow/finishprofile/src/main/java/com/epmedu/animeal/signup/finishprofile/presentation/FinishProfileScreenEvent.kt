package com.epmedu.animeal.signup.finishprofile.presentation

import com.epmedu.animeal.profile.presentation.ProfileInputFormEvent

internal sealed interface FinishProfileScreenEvent {
    data class InputFormEvent(val event: ProfileInputFormEvent) : FinishProfileScreenEvent
    data object Cancel : FinishProfileScreenEvent
    data object Submit : FinishProfileScreenEvent
    data object ScreenDisplayed : FinishProfileScreenEvent
    data object NavigatedToNextDestination : FinishProfileScreenEvent
}