package com.epmedu.animeal.feature.more

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.base.theme.AnimealTheme
import com.epmedu.animeal.feature_more.R

@Composable
fun MoreScreen() {
    AnimealTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    MoreTitle()
                    MoreOptions()
                }
                MoreLogout()
            }
        }
    }
}

@Composable
fun MoreTitle() {
    Text(
        text = stringResource(id = R.string.more_screen_title),
        modifier = Modifier.padding(27.dp),
        style = MaterialTheme.typography.h4,
    )
}

@Composable
fun MoreOptions() {
    Column {
        MoreSpacer()
        MoreOption(name = stringResource(R.string.profile_page_option)) {}
        MoreSpacer()
        MoreOption(name = stringResource(R.string.donate_option)) {}
        MoreSpacer()
        MoreOption(name = stringResource(R.string.help_option)) {}
        MoreSpacer()
        MoreOption(name = stringResource(R.string.about_option)) {}
    }
}

@Composable
fun MoreSpacer() {
    Spacer(modifier = Modifier.height(32.dp))
}

@Composable
fun MoreOption(name: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clickable { onClick() }
            .padding(horizontal = 33.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.body1
        )
        Image(
            painter = painterResource(id = R.drawable.arrow_forward_ios),
            contentDescription = name
        )
    }
}

@Composable
fun MoreLogout() {
    Box(
        modifier = Modifier
            .padding(28.dp)
            .clip(RoundedCornerShape(4.dp))
            .clickable { }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .height(48.dp)
        ) {
            Text(
                text = stringResource(R.string.logout_option),
                style = MaterialTheme.typography.body1
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MoreScreenPreview() {
    MoreScreen()
}