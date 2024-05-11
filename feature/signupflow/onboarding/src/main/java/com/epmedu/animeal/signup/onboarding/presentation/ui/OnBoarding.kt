package com.epmedu.animeal.signup.onboarding.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.spacer.HeightSpacer
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.resources.R

@Composable
internal fun OnBoarding() {
    Column(
        modifier = Modifier.padding(horizontal = 56.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth,
            painter = painterResource(R.drawable.ic_feed_us),
            contentDescription = null
        )
        HeightSpacer(height = 24.dp)
        Text(
            text = stringResource(R.string.onboarding_image_title),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
        )
        HeightSpacer(height = 12.dp)
        Text(
            text = stringResource(R.string.onboarding_image_msg),
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
        )
    }
}

@AnimealPreview
@Composable
private fun OnBoardingPreview() {
    AnimealTheme {
        OnBoarding()
    }
}