package com.epmedu.animeal.signup.onboarding.presentation

internal sealed interface OnboardingScreenEvent {
    data object SignInWithMobileClicked : OnboardingScreenEvent
    data object SignInWithFacebookClicked : OnboardingScreenEvent
    data object FacebookWebUIOpened : OnboardingScreenEvent
    data object SignedInWithFacebook : OnboardingScreenEvent
    data object NotSignedInWithFacebook : OnboardingScreenEvent
    data object ErrorShown : OnboardingScreenEvent
    data object SignInFinished : OnboardingScreenEvent
}