package com.epmedu.animeal.splash.presentation

sealed interface SplashScreenEvent {
    object ErrorShown : SplashScreenEvent
}