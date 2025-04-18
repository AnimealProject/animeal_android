package com.epmedu.animeal.tabs.more.about.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.epmedu.animeal.foundation.button.AnimealSocialButton
import com.epmedu.animeal.foundation.icons.AnimealIcons
import com.epmedu.animeal.foundation.icons.outlined.Facebook
import com.epmedu.animeal.foundation.icons.outlined.Instagram
import com.epmedu.animeal.foundation.icons.outlined.Linkedin
import com.epmedu.animeal.foundation.icons.outlined.Web
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.tabs.more.about.SocialMedia

@Composable
internal fun SocialButtonsRow(
    onSocialClick: (type: SocialMedia) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        AnimealSocialButton(
            onClick = { onSocialClick(SocialMedia.FACEBOOK) },
            icon = AnimealIcons.Outlined.Facebook
        )
        AnimealSocialButton(
            onClick = { onSocialClick(SocialMedia.INSTAGRAM) },
            icon = AnimealIcons.Outlined.Instagram
        )
        AnimealSocialButton(
            onClick = { onSocialClick(SocialMedia.LINKEDIN) },
            icon = AnimealIcons.Outlined.Linkedin
        )
        AnimealSocialButton(
            onClick = { onSocialClick(SocialMedia.WEB) },
            icon = AnimealIcons.Outlined.Web
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