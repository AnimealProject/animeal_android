package com.epmedu.animeal.foundation.common

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.spacer.HeightSpacer
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.resources.R

@Composable
fun AnimealPopUpScreen(
    painterResource: Painter,
    @StringRes titleText: Int,
    @StringRes subtitleText: Int,
    modifier: Modifier = Modifier,
    subtitleModifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            contentScale = ContentScale.None,
            painter = painterResource,
            contentDescription = null,
        )
        HeightSpacer(height = 16.dp)
        Text(
            text = stringResource(titleText),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h4,
            color = MaterialTheme.colors.primary,
            fontWeight = FontWeight.Bold
        )
        HeightSpacer(height = 8.dp)
        Text(
            text = stringResource(subtitleText),
            modifier = subtitleModifier,
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
        )
    }
}

@AnimealPreview
@Composable
private fun AnimealPopUpScreenPreview() {
    AnimealTheme {
        AnimealPopUpScreen(
            painterResource(R.drawable.empty_screen_bone),
            R.string.feeding_tab_all_reviewed_title,
            R.string.feeding_tab_all_reviewed_subtitle
        )
    }
}