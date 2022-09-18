package com.epmedu.animeal.signup

import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.epmedu.animeal.common.route.SignUpRoute
import com.epmedu.animeal.navigation.ScreenNavHost
import com.epmedu.animeal.navigation.route.AuthenticationType
import com.epmedu.animeal.navigation.route.AuthenticationType.Mobile
import com.epmedu.animeal.signup.entercode.presentation.EnterCodeScreen
import com.epmedu.animeal.signup.enterphone.presentation.EnterPhoneScreen
import com.epmedu.animeal.signup.finishprofile.presentation.FinishProfileScreen
import com.epmedu.animeal.signup.onboarding.presentation.OnboardingScreen

@Composable
fun SignUpFlow() {
    var authenticationType by rememberSaveable { mutableStateOf(Mobile) }
    val onAuthenticationTypeChange = { newType: AuthenticationType -> authenticationType = newType }

    ScreenNavHost(
        modifier = Modifier.statusBarsPadding(),
        startDestination = SignUpRoute.Onboarding.name
    ) {
        screen(SignUpRoute.Onboarding.name) { OnboardingScreen(onAuthenticationTypeChange) }
        screen(SignUpRoute.EnterPhone.name) { EnterPhoneScreen() }
        screen(SignUpRoute.EnterCode.name) { EnterCodeScreen(authenticationType) }
        screen(SignUpRoute.FinishProfile.name) { FinishProfileScreen(authenticationType) }
    }
}
