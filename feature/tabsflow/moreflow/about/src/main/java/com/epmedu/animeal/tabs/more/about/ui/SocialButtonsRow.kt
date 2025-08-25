package com.epmedu.animeal.tabs.more.about.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.epmedu.animeal.foundation.button.AnimealSocialButton
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.resources.R
import com.epmedu.animeal.tabs.more.about.LinkMediaType

@Composable
internal fun SocialButtonsRow(
    onSocialClick: (type: LinkMediaType) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        AnimealSocialButton(
            onClick = { onSocialClick(LinkMediaType.FACEBOOK) },
            iconResource = R.drawable.ic_facebook
        )
        AnimealSocialButton(
            onClick = { onSocialClick(LinkMediaType.INSTAGRAM) },
            iconResource = R.drawable.ic_instagram
        )
        AnimealSocialButton(
            onClick = { onSocialClick(LinkMediaType.LINKEDIN) },
            iconResource = R.drawable.ic_linkedin
        )
        AnimealSocialButton(
            onClick = { onSocialClick(LinkMediaType.ANIMEAL) },
            iconResource = R.drawable.ic_web
        )
    }
}

@AnimealPreview
@Composable
private fun SocialButtonsRowPreview() {
    AnimealTheme {
        SocialButtonsRow(onSocialClick = {})
    }
}