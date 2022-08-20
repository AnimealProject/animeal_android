package com.epmedu.animeal.signup.onboarding.presentation.ui

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
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
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.resources.R

@Composable
internal fun ColumnScope.OnBoarding(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(bottom = 32.dp)
            .weight(1f),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
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

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun OnBoardingPreview() {
    AnimealTheme {
        Surface {
            Column {
                OnBoarding()
            }
        }
    }
}