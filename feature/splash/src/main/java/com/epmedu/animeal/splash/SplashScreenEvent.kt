package com.epmedu.animeal.splash

sealed interface SplashScreenEvent {
    object NavigateToHome : SplashScreenEvent
    object NavigateToOnboarding : SplashScreenEvent
}