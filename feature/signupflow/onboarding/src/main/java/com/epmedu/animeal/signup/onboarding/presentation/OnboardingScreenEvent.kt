package com.epmedu.animeal.signup.onboarding.presentation

internal sealed interface OnboardingScreenEvent {
    data object SignInWithMobileClicked : OnboardingScreenEvent
    data object SignInGuestClicked : OnboardingScreenEvent
    data object ErrorShown : OnboardingScreenEvent
    data object SignInFinished : OnboardingScreenEvent
}