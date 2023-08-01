package com.epmedu.animeal.tabs.more.about.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.topbar.BackButton
import com.epmedu.animeal.foundation.topbar.TopBar
import com.epmedu.animeal.resources.R

@Composable
internal fun AboutHeading(onBack: () -> Unit, horizontalPadding: Dp) {
    Column {
        TopBar(
            title = stringResource(id = R.string.page_about),
            navigationIcon = {
                BackButton(onClick = onBack)
            }
        )
        Image(
            modifier = Modifier
                .padding(top = 30.dp)
                .padding(horizontal = horizontalPadding)
                .height(190.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.FillBounds,
            painter = painterResource(R.drawable.about),
            contentDescription = null,
        )
    }
}

@AnimealPreview
@Composable
private fun AboutHeadingPreview() {
    AnimealTheme {
        AboutHeading({}, 0.dp)
    }
}