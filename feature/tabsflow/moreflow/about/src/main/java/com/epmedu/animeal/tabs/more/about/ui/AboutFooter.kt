package com.epmedu.animeal.tabs.more.about.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.theme.CustomColor
import com.epmedu.animeal.resources.R
import com.epmedu.animeal.tabs.more.about.LinkMediaType

@Composable
internal fun AboutFooter(
    currentVersion: String,
    onLinkClick: (type: LinkMediaType) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        SocialButtonsRow(
            onSocialClick = onLinkClick
        )
        Text(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth(),
            text = stringResource(id = R.string.about_app_version, currentVersion),
            style = MaterialTheme.typography.body1,
            color = CustomColor.TextGrey,
            textAlign = TextAlign.Center
        )
    }
}

@AnimealPreview
@Composable
private fun AboutFooterPreview() {
    AnimealTheme {
        AboutFooter(
            currentVersion = "",
            onLinkClick = {},
            modifier = Modifier.padding(horizontal = 36.dp)
        )
    }
}
