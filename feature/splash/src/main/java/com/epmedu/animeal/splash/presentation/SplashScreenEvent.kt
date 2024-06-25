package com.epmedu.animeal.splash.presentation

sealed interface SplashScreenEvent {
    data object ErrorShown : SplashScreenEvent
}