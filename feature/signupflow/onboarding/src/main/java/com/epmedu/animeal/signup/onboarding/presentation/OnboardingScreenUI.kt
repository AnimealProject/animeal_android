package com.epmedu.animeal.signup.onboarding.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.signup.onboarding.presentation.ui.ButtonsBlock
import com.epmedu.animeal.signup.onboarding.presentation.ui.LegalLinks
import com.epmedu.animeal.signup.onboarding.presentation.ui.OnBoarding

@Composable
internal fun OnboardingScreenUI(
    onSignInMobile: () -> Unit,
    onContinueClick: () -> Unit
) {
    Column {
        Spacer(modifier = Modifier.weight(1f))
        OnBoarding()
        Spacer(
            modifier = Modifier.weight(5f)
        )
        ButtonsBlock(
            onSignInMobile = onSignInMobile,
            onContinueClick = onContinueClick,
        )
        Spacer(modifier = Modifier.weight(0.5f))
        LegalLinks()
        Spacer(modifier = Modifier.weight(0.5f))
    }
}

@AnimealPreview
@Composable
private fun OnboardingScreenWithoutFacebookPreview() {
    AnimealTheme {
        OnboardingScreenUI(
            onSignInMobile = {},
            onContinueClick = {},
        )
    }
}

@AnimealPreview
@Composable
private fun OnboardingScreenWithFacebookPreview() {
    AnimealTheme {
        OnboardingScreenUI(
            onSignInMobile = {},
            onContinueClick = {},
        )
    }
}
