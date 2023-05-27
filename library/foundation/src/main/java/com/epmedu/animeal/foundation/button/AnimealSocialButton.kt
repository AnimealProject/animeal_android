package com.epmedu.animeal.foundation.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.resources.R

@Composable
fun AnimealSocialButton(
    iconResource: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colors.surface,
) {
    FloatingActionButton(
        modifier = modifier.size(62.dp),
        onClick = onClick,
        backgroundColor = backgroundColor,
    ) {
        Icon(
            painter = painterResource(id = iconResource),
            contentDescription = null
        )
    }
}

@AnimealPreview
@Composable
private fun AnimealSocialButtonPreview() {
    AnimealTheme {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            AnimealSocialButton(
                onClick = { },
                iconResource = R.drawable.ic_facebook
            )
            AnimealSocialButton(
                onClick = { },
                iconResource = R.drawable.ic_instagram
            )
            AnimealSocialButton(
                onClick = { },
                iconResource = R.drawable.ic_linkedin
            )
            AnimealSocialButton(
                onClick = { },
                iconResource = R.drawable.ic_web
            )
        }
    }
}
