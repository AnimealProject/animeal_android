package com.epmedu.animeal.signup

import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.epmedu.animeal.common.route.SignUpRoute
import com.epmedu.animeal.navigation.ScreenNavHost
import com.epmedu.animeal.signup.entercode.presentation.EnterCodeScreen
import com.epmedu.animeal.signup.enterphone.presentation.EnterPhoneScreen
import com.epmedu.animeal.signup.finishprofile.presentation.FinishProfileScreen
import com.epmedu.animeal.signup.onboarding.presentation.OnboardingScreen

@Composable
fun SignUpFlow() {
    ScreenNavHost(
        modifier = Modifier.statusBarsPadding(),
        startDestination = SignUpRoute.Onboarding.name
    ) {
        screen(SignUpRoute.Onboarding.name) { OnboardingScreen() }
        screen(SignUpRoute.EnterPhone.name) { EnterPhoneScreen() }
        screen(SignUpRoute.EnterCode.name) { EnterCodeScreen() }
        screen(SignUpRoute.FinishProfile.name) { FinishProfileScreen() }
    }
}
