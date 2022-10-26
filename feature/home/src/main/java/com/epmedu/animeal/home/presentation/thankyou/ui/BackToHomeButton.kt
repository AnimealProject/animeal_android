package com.epmedu.animeal.home.presentation.thankyou.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.button.AnimealButton
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.resources.R

@Composable
internal fun BackToHomeButton(onClick: () -> Unit) {
    Column {
        AnimealButton(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(30.dp),
            text = stringResource(R.string.back_to_home),
            onClick = onClick
        )
    }
}

@AnimealPreview
@Composable
private fun BackToHomeButtonPreview() {
    AnimealTheme {
        BackToHomeButton(
            onClick = {}
        )
    }
}
