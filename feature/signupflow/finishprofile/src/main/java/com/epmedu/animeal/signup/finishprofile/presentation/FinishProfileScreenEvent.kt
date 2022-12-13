package com.epmedu.animeal.signup.finishprofile.presentation

internal sealed interface FinishProfileScreenEvent {
    object Cancel : FinishProfileScreenEvent
    object Submit : FinishProfileScreenEvent
}