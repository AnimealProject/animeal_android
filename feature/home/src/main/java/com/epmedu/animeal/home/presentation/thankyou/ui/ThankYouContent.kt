package com.epmedu.animeal.home.presentation.thankyou.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.resources.R

@Composable
internal fun ThankYouContent() {
    Column(
        modifier = Modifier
            .padding(24.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            contentScale = ContentScale.None,
            painter = painterResource(R.drawable.ic_paw),
            contentDescription = null,
        )
        Text(
            text = stringResource(R.string.thank_you),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h3,
            color = MaterialTheme.colors.primary,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(R.string.animals_fed),
            style = MaterialTheme.typography.body2,
        )
    }
}

@AnimealPreview
@Composable
private fun ThankYouContentPreview() {
    AnimealTheme {
        ThankYouContent()
    }
}
