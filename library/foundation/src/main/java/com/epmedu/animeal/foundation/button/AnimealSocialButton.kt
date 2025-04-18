package com.epmedu.animeal.foundation.button

import androidx.compose.foundation.layout.size
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.icons.AnimealIcons
import com.epmedu.animeal.foundation.icons.outlined.Facebook
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme

@Composable
fun AnimealSocialButton(
    icon: ImageVector,
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
            imageVector = icon,
            contentDescription = null
        )
    }
}

@AnimealPreview
@Composable
private fun AnimealSocialButtonPreview() {
    AnimealTheme {
        AnimealSocialButton(
            onClick = { },
            icon = AnimealIcons.Outlined.Facebook
        )
    }
}
