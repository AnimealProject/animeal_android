package com.epmedu.animeal.signup.onboarding.presentation.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.button.AnimealButton
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.theme.CustomColor
import com.epmedu.animeal.resources.R

@Composable
internal fun ButtonsBlock(
    modifier: Modifier = Modifier,
    onSignInMobile: () -> Unit,
    onSignInFacebook: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp),
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

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun ButtonsBlockPreview() {
    AnimealTheme {
        Surface {
            ButtonsBlock(
                onSignInMobile = {},
                onSignInFacebook = {}
            )
        }
    }
}