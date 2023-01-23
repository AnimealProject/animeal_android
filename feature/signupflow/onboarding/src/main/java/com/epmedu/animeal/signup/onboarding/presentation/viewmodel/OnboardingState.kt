package com.epmedu.animeal.signup.onboarding.presentation.viewmodel

import com.epmedu.animeal.auth.AuthenticationType

data class OnboardingState(
    val authenticationType: AuthenticationType? = null
)