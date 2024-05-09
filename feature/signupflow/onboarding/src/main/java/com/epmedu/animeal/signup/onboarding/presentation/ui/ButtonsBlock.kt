package com.epmedu.animeal.signup.onboarding.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.button.AnimealButton
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.theme.CustomColor
import com.epmedu.animeal.resources.R

@Composable
internal fun ButtonsBlock(
    isFacebookButtonAvailable: Boolean,
    onSignInMobile: () -> Unit,
    onSignInFacebook: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AnimealButton(
            onClick = onSignInMobile,
            contentColor = MaterialTheme.colors.onPrimary,
        ) {
            LoginButtonContent(
                iconId = R.drawable.ic_phone,
                textId = R.string.sign_in_mobile,
                tint = MaterialTheme.colors.onPrimary
            )
        }
        if (isFacebookButtonAvailable) {
            AnimealButton(
                backgroundColor = CustomColor.Facebook,
                contentColor = MaterialTheme.colors.onPrimary,
                onClick = onSignInFacebook,
            ) {
                LoginButtonContent(
                    iconId = R.drawable.ic_facebook,
                    textId = R.string.sign_in_facebook,
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        }
    }
}

@AnimealPreview
@Composable
private fun ButtonsBlockPreview() {
    AnimealTheme {
        Column {
            ButtonsBlock(
                isFacebookButtonAvailable = true,
                onSignInMobile = {},
                onSignInFacebook = {}
            )
            Divider()
            ButtonsBlock(
                isFacebookButtonAvailable = false,
                onSignInMobile = {},
                onSignInFacebook = {}
            )
        }
    }
}