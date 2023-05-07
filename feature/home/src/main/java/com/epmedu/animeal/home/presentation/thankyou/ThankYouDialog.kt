package com.epmedu.animeal.home.presentation.thankyou

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.button.AnimealButton
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.home.presentation.thankyou.ui.ThankYouContent
import com.epmedu.animeal.resources.R

@Composable
internal fun ThankYouDialog(
    onDismiss: () -> Unit
) {
    BackHandler(enabled = true) {
        onDismiss()
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.secondaryVariant)
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))
            ThankYouContent()
            Spacer(modifier = Modifier.weight(1f))
            AnimealButton(
                modifier = Modifier.padding(30.dp),
                text = stringResource(R.string.back_to_home),
                onClick = onDismiss
            )
        }
    }
}

@AnimealPreview
@Composable
private fun ThankYouScreenPreview() {
    AnimealTheme {
        ThankYouDialog(
            onDismiss = {},
        )
    }
}