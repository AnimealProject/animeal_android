package com.epmedu.animeal.signup.finishprofile.presentation.viewmodel

internal sealed interface FinishProfileEvent {
    object NavigateBackToOnboarding : FinishProfileEvent
    object NavigateBackToEnterPhone : FinishProfileEvent
    object ProfileFinished : FinishProfileEvent
    object NavigateToConfirmPhone : FinishProfileEvent
}