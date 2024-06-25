package com.epmedu.animeal.signup.entercode.presentation.viewmodel

internal sealed interface EnterCodeEvent {
    data object NavigateToFinishProfile : EnterCodeEvent
    data object NavigateToHomeScreen : EnterCodeEvent
}