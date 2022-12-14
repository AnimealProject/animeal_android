package com.epmedu.animeal.tabs.more.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.SpaceEvenly
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epmedu.animeal.foundation.button.AnimealSocialButton
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.topbar.BackButton
import com.epmedu.animeal.foundation.topbar.TopBar
import com.epmedu.animeal.resources.R

@Composable
internal fun AboutScreenUI(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    onSocialFacebookClick: () -> Unit,
    onSocialInstagramClick: () -> Unit,
    onSocialLinkedinClick: () -> Unit,
    onSocialWebClick: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar(
            title = stringResource(id = R.string.page_about),
            navigationIcon = { BackButton(onClick = onBack) }
        )
        Image(
            modifier = Modifier.padding(start = 64.dp, end = 64.dp, top = 18.dp, bottom = 18.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Fit,
            painter = painterResource(R.drawable.about),
            contentDescription = null,
        )
        Text(
            modifier = Modifier.padding(start = 32.dp, end = 32.dp, top = 12.dp, bottom = 12.dp),
            text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, " +
                "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " +
                "laboris nisi ut aliquip ex ea commodo consequat.",
            style = MaterialTheme.typography.body2,
            lineHeight = 20.sp,
        )
        Text(
            modifier = Modifier.padding(start = 32.dp, end = 32.dp, top = 12.dp, bottom = 18.dp),
            text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, " +
                "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " +
                "laboris nisi ut aliquip ex ea commodo consequat.",
            style = MaterialTheme.typography.body2,
            lineHeight = 20.sp,
        )
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, end = 4.dp, top = 12.dp, bottom = 32.dp),
            horizontalArrangement = SpaceEvenly,
        ) {
            AnimealSocialButton(
                onClick = onSocialFacebookClick,
                iconResource = R.drawable.icon_fb
            )
            AnimealSocialButton(
                onClick = onSocialInstagramClick,
                iconResource = R.drawable.icon_inst
            )
            AnimealSocialButton(
                onClick = onSocialLinkedinClick,
                iconResource = R.drawable.icon_link
            )
            AnimealSocialButton(
                onClick = onSocialWebClick,
                iconResource = R.drawable.icon_web
            )
        }
    }
}

@AnimealPreview
@Composable
private fun AboutScreenUIPreview() {
    AnimealTheme {
        AboutScreenUI(
            onBack = {},
            onSocialFacebookClick = {},
            onSocialInstagramClick = {},
            onSocialLinkedinClick = {},
            onSocialWebClick = {},
        )
    }
}
