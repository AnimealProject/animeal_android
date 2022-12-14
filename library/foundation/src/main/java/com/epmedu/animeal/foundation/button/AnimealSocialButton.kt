package com.epmedu.animeal.foundation.button

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
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    backgroundColor: Color = MaterialTheme.colors.surface,
    iconResource: Int
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
private fun FbAnimealSocialButtonPreview() {
    AnimealTheme {
        AnimealSocialButton(
            onClick = {},
            iconResource = R.drawable.icon_fb
        )
    }
}

@AnimealPreview
@Composable
private fun InstAnimealSocialButtonPreview() {
    AnimealTheme {
        AnimealSocialButton(
            onClick = {},
            iconResource = R.drawable.icon_inst
        )
    }
}

@AnimealPreview
@Composable
private fun LinkAnimealSocialButtonPreview() {
    AnimealTheme {
        AnimealSocialButton(
            onClick = {},
            iconResource = R.drawable.icon_link
        )
    }
}

@AnimealPreview
@Composable
private fun WebAnimealSocialButtonPreview() {
    AnimealTheme {
        AnimealSocialButton(
            onClick = {},
            iconResource = R.drawable.icon_web
        )
    }
}
