package com.epmedu.animeal.signup.finishprofile.presentation.viewmodel

internal sealed interface FinishProfileEvent {
    object Saved : FinishProfileEvent
}