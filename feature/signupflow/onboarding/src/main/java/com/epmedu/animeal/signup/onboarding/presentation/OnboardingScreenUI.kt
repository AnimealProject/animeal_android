package com.epmedu.animeal.signup.onboarding.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.signup.onboarding.presentation.ui.ButtonsBlock
import com.epmedu.animeal.signup.onboarding.presentation.ui.OnBoarding
import com.epmedu.animeal.signup.onboarding.presentation.viewmodel.OnboardingState

@Composable
internal fun OnboardingScreenUI(
    state: OnboardingState,
    onSignInMobile: () -> Unit,
    onSignInFacebook: () -> Unit
) {
    Column {
        Spacer(modifier = Modifier.weight(1f))
        OnBoarding()
        Spacer(
            modifier = Modifier.weight(if (state.isFacebookLoginAvailable) 5f else 4f)
        )
        ButtonsBlock(
            isFacebookButtonAvailable = state.isFacebookLoginAvailable,
            onSignInMobile = onSignInMobile,
            onSignInFacebook = onSignInFacebook,
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

@AnimealPreview
@Composable
private fun OnboardingScreenWithoutFacebookPreview() {
    AnimealTheme {
        OnboardingScreenUI(
            state = OnboardingState(),
            onSignInMobile = {},
            onSignInFacebook = {},
        )
    }
}

@AnimealPreview
@Composable
private fun OnboardingScreenWithFacebookPreview() {
    AnimealTheme {
        OnboardingScreenUI(
            state = OnboardingState(isFacebookLoginAvailable = true),
            onSignInMobile = {},
            onSignInFacebook = {},
        )
    }
}
