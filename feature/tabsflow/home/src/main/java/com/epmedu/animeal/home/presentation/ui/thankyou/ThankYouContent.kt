package com.epmedu.animeal.home.presentation.ui.thankyou

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import com.epmedu.animeal.foundation.common.AnimealPopUpScreen
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.resources.R

@Composable
internal fun ThankYouContent() {
    AnimealPopUpScreen(
        painterResource = painterResource(id = R.drawable.ic_paw),
        titleText = R.string.thank_you,
        subtitleText = R.string.animals_fed
    )
}

@AnimealPreview
@Composable
private fun ThankYouContentPreview() {
    AnimealTheme {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ThankYouContent()
        }
    }
}
