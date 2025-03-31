package com.epmedu.animeal.signup.onboarding.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.button.AnimealButton
import com.epmedu.animeal.foundation.button.AnimealSecondaryButtonOutlined
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.resources.R

@Composable
internal fun ButtonsBlock(
    onSignInMobile: () -> Unit,
    continueClicked: () -> Unit,
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
        AnimealSecondaryButtonOutlined(onClick = continueClicked) {
            LoginButtonContent(
                iconId = R.drawable.ic_sign_in_guest,
                textId = R.string.sign_in_as_guest,
                tint = MaterialTheme.colors.primaryVariant
            )
        }
    }
}

@AnimealPreview
@Composable
private fun ButtonsBlockPreview() {
    AnimealTheme {
        ButtonsBlock(
            onSignInMobile = {},
            continueClicked = {}
        )
    }
}