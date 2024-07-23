package com.epmedu.animeal.signup.entercode.presentation

internal sealed interface EnterCodeScreenEvent {
    data object ScreenDisplayed : EnterCodeScreenEvent
    data class NumberChanged(val position: Int, val number: String?) : EnterCodeScreenEvent
    data object ResendCode : EnterCodeScreenEvent
    data class ReadSMS(val message: String) : EnterCodeScreenEvent
}