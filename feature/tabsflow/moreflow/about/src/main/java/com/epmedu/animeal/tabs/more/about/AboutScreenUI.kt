package com.epmedu.animeal.tabs.more.about

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.epmedu.animeal.foundation.util.generateLoremIpsum
import com.epmedu.animeal.resources.R

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
internal fun AboutScreenUI(
    onBack: () -> Unit,
    onSocialClick: (type: SocialMedia) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .statusBarsPadding(),
    ) {
        TopBar(
            title = stringResource(id = R.string.page_about),
            navigationIcon = {
                BackButton(onClick = onBack)
            }
        )
        Column(
            modifier = Modifier
                .padding(horizontal = 36.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .padding(horizontal = 32.dp, vertical = 18.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Fit,
                painter = painterResource(R.drawable.about),
                contentDescription = null,
            )
            Text(
                modifier = Modifier.padding(vertical = 12.dp),
                text = generateLoremIpsum(29..30),
                style = MaterialTheme.typography.body2,
                lineHeight = 20.sp,
            )
            Text(
                modifier = Modifier.padding(vertical = 12.dp),
                text = generateLoremIpsum(29..30),
                style = MaterialTheme.typography.body2,
                lineHeight = 20.sp,
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        SocialButtonsRow(
            onSocialClick = onSocialClick
        )
    }
}

@Composable
internal fun SocialButtonsRow(
    onSocialClick: (type: SocialMedia) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(36.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        AnimealSocialButton(
            onClick = { onSocialClick(SocialMedia.FACEBOOK) },
            iconResource = R.drawable.ic_facebook
        )
        AnimealSocialButton(
            onClick = { onSocialClick(SocialMedia.INSTAGRAM) },
            iconResource = R.drawable.ic_instagram
        )
        AnimealSocialButton(
            onClick = { onSocialClick(SocialMedia.LINKEDIN) },
            iconResource = R.drawable.ic_linkedin
        )
        AnimealSocialButton(
            onClick = { onSocialClick(SocialMedia.WEB) },
            iconResource = R.drawable.ic_web
        )
    }
}

@AnimealPreview
@Composable
private fun AboutScreenUIPreview() {
    AnimealTheme {
        AboutScreenUI(
            onBack = {},
            onSocialClick = {}
        )
    }
}
