package com.epmedu.animeal.signup.onboarding.presentation

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.signup.onboarding.presentation.ui.ButtonsBlock
import com.epmedu.animeal.signup.onboarding.presentation.ui.OnBoarding

@Composable
internal fun OnboardingScreenUI(
    modifier: Modifier = Modifier,
    onSignInMobile: () -> Unit,
    onSignInFacebook: () -> Unit,
) {
    Surface(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            OnBoarding()
            ButtonsBlock(
                onSignInMobile = onSignInMobile,
                onSignInFacebook = onSignInFacebook,
            )
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun OnboardingScreenPreview() {
    AnimealTheme {
        OnboardingScreenUI(
            onSignInMobile = {},
            onSignInFacebook = {},
        )
    }
}
