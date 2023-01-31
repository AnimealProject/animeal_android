package com.epmedu.animeal.signup.entercode.presentation.viewmodel

internal sealed interface EnterCodeEvent {
    object NavigateToFinishProfile : EnterCodeEvent
    object NavigateToHomeScreen : EnterCodeEvent
}