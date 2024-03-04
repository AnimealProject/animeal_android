package com.epmedu.animeal.tabs.analytics

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.spacer.HeightSpacer
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.topbar.TopBar
import com.epmedu.animeal.resources.R

@Composable
internal fun AnalyticsScreenUi() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        TopBar(
            title = stringResource(id = R.string.feeding_analysis)
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(0.85f))
            Box(contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(id = R.drawable.ic_sticker),
                    contentDescription = null,
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_analysis),
                    contentDescription = null,
                    modifier = Modifier.padding(bottom = 12.dp, end = 8.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_magnifying_glass),
                    contentDescription = null,
                    modifier = Modifier.padding(top = 12.dp, start = 8.dp)
                )
            }
            HeightSpacer(height = 16.dp)
            Text(
                text = stringResource(id = R.string.coming_soon),
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.h1
            )
            HeightSpacer(height = 8.dp)
            Text(text = stringResource(id = R.string.thank_you_for_visiting))
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@AnimealPreview
@Composable
private fun AnalyticsScreenUiPreview() {
    AnimealTheme {
        AnalyticsScreenUi()
    }
}