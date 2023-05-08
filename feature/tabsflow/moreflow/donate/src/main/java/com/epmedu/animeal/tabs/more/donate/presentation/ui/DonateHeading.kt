package com.epmedu.animeal.tabs.more.donate.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.spacer.HeightSpacer
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.topbar.BackButton
import com.epmedu.animeal.foundation.topbar.TopBar
import com.epmedu.animeal.resources.R

@Composable
internal fun DonateHeading(onBack: () -> Unit) {
    Column {
        TopBar(
            title = stringResource(id = R.string.page_donate),
            modifier = Modifier.statusBarsPadding(),
            navigationIcon = {
                BackButton(onClick = onBack)
            }
        )
        HeightSpacer(height = 10.dp)
        Text(
            modifier = Modifier.padding(horizontal = 26.dp),
            style = MaterialTheme.typography.subtitle1,
            text = stringResource(R.string.donation_subtitle),
        )
        HeightSpacer(height = 43.dp)
        Image(
            modifier = Modifier
                .padding(horizontal = 37.dp)
                .clip(RoundedCornerShape(12.dp))
                .aspectRatio(2.0f),
            painter = painterResource(id = R.drawable.doggo),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}

@AnimealPreview
@Composable
private fun DonateTitleAndSubtitlePreview() {
    AnimealTheme {
        DonateHeading(onBack = {})
    }
}