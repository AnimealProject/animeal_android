package com.epmedu.animeal.signup.onboarding.presentation

internal sealed interface OnboardingScreenEvent {
    object SignInWithMobileClicked : OnboardingScreenEvent
    object SignInWithFacebookClicked : OnboardingScreenEvent
    object FacebookWebUIOpened : OnboardingScreenEvent
    object SignedInWithFacebook : OnboardingScreenEvent
    object NotSignedInWithFacebook : OnboardingScreenEvent
    object ErrorShown : OnboardingScreenEvent
    object SignInFinished : OnboardingScreenEvent
}