package com.epmedu.animeal.signup.onboarding.presentation

sealed class OnboardingScreenEvent {
    object RedirectedFromFacebookWebUi : OnboardingScreenEvent()
    object FacebookSignInFinished : OnboardingScreenEvent()
    object SignInWithMobileClicked : OnboardingScreenEvent()
}