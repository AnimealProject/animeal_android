package com.epmedu.animeal.signup.onboarding.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.signup.onboarding.presentation.ui.ButtonsBlock
import com.epmedu.animeal.signup.onboarding.presentation.ui.OnBoarding

@Composable
internal fun OnboardingScreenUI(
    modifier: Modifier = Modifier,
    onSignInMobile: () -> Unit,
    onSignInFacebook: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        OnBoarding()
        ButtonsBlock(
            onSignInMobile = onSignInMobile,
            onSignInFacebook = onSignInFacebook,
        )
    }
}

@AnimealPreview
@Composable
private fun OnboardingScreenPreview() {
    AnimealTheme {
        OnboardingScreenUI(
            onSignInMobile = {},
            onSignInFacebook = {},
        )
    }
}
