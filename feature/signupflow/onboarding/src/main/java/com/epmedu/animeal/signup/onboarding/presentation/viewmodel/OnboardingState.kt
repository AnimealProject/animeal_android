package com.epmedu.animeal.signup.onboarding.presentation.viewmodel

import com.epmedu.animeal.signup.onboarding.domain.FacebookAuthorization

data class OnboardingState(
    val facebookAuthorization: FacebookAuthorization? = null
)