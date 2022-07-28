package com.epmedu.animeal.login

import androidx.compose.runtime.Composable
import com.epmedu.animeal.login.code.presentation.EnterCodeScreen
import com.epmedu.animeal.login.phone.presentation.EnterPhoneScreen
import com.epmedu.animeal.login.profile.presentation.FinishProfileScreen
import com.epmedu.animeal.login.signin.presentation.SignInScreen
import com.epmedu.animeal.navigation.ScreenNavHost

@Composable
fun OnboardingScreen() {
    ScreenNavHost(
        startDestination = OnboardingScreenRoute.SignIn.name
    ) {
        screen(OnboardingScreenRoute.SignIn.name) { SignInScreen() }
        screen(OnboardingScreenRoute.EnterPhone.name) { EnterPhoneScreen() }
        screen(OnboardingScreenRoute.EnterCode.name) { EnterCodeScreen() }
        screen(OnboardingScreenRoute.FinishProfile.name) { FinishProfileScreen() }
    }
}

internal enum class OnboardingScreenRoute {
    SignIn, EnterPhone, EnterCode, FinishProfile
}