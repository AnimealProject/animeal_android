package com.epmedu.animeal.login.signin.presentation

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.button.AnimealButton
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.theme.CustomColor
import com.epmedu.animeal.login.signin.ui.LoginButtonContent
import com.epmedu.animeal.resources.R

@Composable
internal fun SignInScreenUI(
    modifier: Modifier = Modifier,
    onSignInMobile: () -> Unit,
    onSignInFacebook: () -> Unit,
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colors.background,
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
}

@Composable
private fun ColumnScope.OnBoarding(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(bottom = 32.dp)
            .weight(1f),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            Image(
                modifier = Modifier.weight(1f),
                contentScale = ContentScale.Crop,
                painter = painterResource(R.drawable.ic_feed_us),
                contentDescription = null
            )
            Text(
                text = stringResource(R.string.onboarding_image_title),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.h5,
                textAlign = TextAlign.Center,
            )
            Text(
                text = stringResource(R.string.onboarding_image_msg),
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
private fun ButtonsBlock(
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

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun SignInScreenPreview() {
    AnimealTheme {
        SignInScreenUI(
            onSignInMobile = {},
            onSignInFacebook = {},
        )
    }
}
