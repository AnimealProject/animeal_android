package com.epmedu.animeal.signup.presentation.viewmodel

import com.epmedu.animeal.common.route.SignUpRoute

data class SignUpState(
    val startDestination: SignUpRoute = SignUpRoute.Onboarding,
    val isLoading: Boolean = false
)
