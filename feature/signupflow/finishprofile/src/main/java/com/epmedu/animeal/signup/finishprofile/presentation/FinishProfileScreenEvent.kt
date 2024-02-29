package com.epmedu.animeal.signup.finishprofile.presentation

import com.epmedu.animeal.profile.presentation.ProfileInputFormEvent

internal sealed interface FinishProfileScreenEvent {
    data class InputFormEvent(val event: ProfileInputFormEvent): FinishProfileScreenEvent
    object Cancel : FinishProfileScreenEvent
    object Submit : FinishProfileScreenEvent
    object ScreenDisplayed : FinishProfileScreenEvent
    object NavigatedToNextDestination : FinishProfileScreenEvent
}