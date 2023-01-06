package com.epmedu.animeal.tabs.more.about

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.SpaceAround
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
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
    onSocialFacebookClick: () -> Unit,
    onSocialInstagramClick: () -> Unit,
    onSocialLinkedinClick: () -> Unit,
    onSocialWebClick: () -> Unit,
    text: String,
    text2: String,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        topBar = {
            TopBar(
                title = stringResource(id = R.string.page_about),
                navigationIcon = {
                    BackButton(onClick = onBack)
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.padding(horizontal = 32.dp, vertical = 18.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Fit,
                painter = painterResource(R.drawable.about),
                contentDescription = null,
            )
            Text(
                modifier = Modifier.padding(vertical = 12.dp),
                text = text,
                style = MaterialTheme.typography.body2,
                lineHeight = 20.sp,
            )
            Text(
                modifier = Modifier.padding(vertical = 12.dp),
                text = text2,
                style = MaterialTheme.typography.body2,
                lineHeight = 20.sp,
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                horizontalArrangement = SpaceAround,
            ) {
                AnimealSocialButton(
                    onClick = onSocialFacebookClick,
                    iconResource = R.drawable.ic_facebook
                )
                AnimealSocialButton(
                    onClick = onSocialInstagramClick,
                    iconResource = R.drawable.ic_instagram
                )
                AnimealSocialButton(
                    onClick = onSocialLinkedinClick,
                    iconResource = R.drawable.ic_linkedin
                )
                AnimealSocialButton(
                    onClick = onSocialWebClick,
                    iconResource = R.drawable.ic_web
                )
            }
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
            text = generateLoremIpsum(29..30),
            text2 = generateLoremIpsum(29..30),
        )
    }
}
