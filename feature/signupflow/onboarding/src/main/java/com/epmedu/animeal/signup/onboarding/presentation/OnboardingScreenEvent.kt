package com.epmedu.animeal.signup.onboarding.presentation

sealed class OnboardingScreenEvent {
    object RedirectedFromFacebookWebUi : OnboardingScreenEvent()
    object SignInFinished : OnboardingScreenEvent()
    object SignInWithMobileClicked : OnboardingScreenEvent()
    object SignInWithFacebookClicked : OnboardingScreenEvent()
}