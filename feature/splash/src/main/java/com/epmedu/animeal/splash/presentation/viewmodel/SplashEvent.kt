package com.epmedu.animeal.splash.presentation.viewmodel

sealed interface SplashEvent {
    object NavigateToHome : SplashEvent
    object NavigateToOnboarding : SplashEvent
}