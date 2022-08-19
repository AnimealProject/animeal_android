package com.epmedu.animeal.signup.onboarding.presentation.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.button.AnimealButton
import com.epmedu.animeal.foundation.spacer.WidthSpacer
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.resources.R

@Composable
internal fun LoginButtonContent(
    @DrawableRes iconId: Int,
    @StringRes textId: Int,
    tint: Color? = null
) {
    Image(
        modifier = Modifier.height(20.dp),
        painter = painterResource(id = iconId),
        contentDescription = null,
        colorFilter = when {
            tint != null -> ColorFilter.tint(tint)
            else -> null
        }
    )
    WidthSpacer(16.dp)
    Text(
        text = stringResource(id = textId),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
    )
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun LoginButtonContentPreview() {
    AnimealTheme {
        AnimealButton(onClick = {}) {
            LoginButtonContent(
                iconId = R.drawable.ic_phone,
                textId = R.string.sign_in_mobile,
                tint = MaterialTheme.colors.onPrimary
            )
        }
    }
}
