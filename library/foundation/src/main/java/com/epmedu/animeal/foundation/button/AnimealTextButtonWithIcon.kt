@file: Suppress("LongParameterList")

package com.epmedu.animeal.foundation.button

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme

@Composable
fun AnimealTextButtonWithIcon(
    icon: Painter,
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Center,
    color: Color = Color.Unspecified,
    contentDescription: String? = null,
    onClick: () -> Unit,
) = AnimealTextButtonWithIcon(
    modifier = modifier,
    icon = {
        Icon(
            painter = icon,
            contentDescription = contentDescription,
            tint = color
        )
    },
    text = text,
    textAlign = textAlign,
    color = color,
    onClick = onClick,
)

@Composable
fun AnimealTextButtonWithIcon(
    icon: ImageVector,
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Center,
    color: Color = Color.Unspecified,
    contentDescription: String? = null,
    onClick: () -> Unit,
) = AnimealTextButtonWithIcon(
    modifier = modifier,
    icon = {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = color
        )
    },
    text = text,
    textAlign = textAlign,
    color = color,
    onClick = onClick,
)

@Composable
internal fun AnimealTextButtonWithIcon(
    icon: @Composable () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign,
    color: Color = Color.Unspecified,
    enabled: Boolean = true,
    onClick: () -> Unit,
) = TextButton(
    modifier = modifier.height(48.dp),
    enabled = enabled,
    onClick = onClick
) {
    icon()

    Text(
        modifier = Modifier.fillMaxWidth().padding(start = 8.dp),
        text = text,
        color = color,
        textAlign = textAlign,
        style = TextStyle(
            fontSize = 16.sp,
            letterSpacing = 0.sp,
            lineHeight = 0.sp,
            textAlign = TextAlign.Start,
            fontFamily = FontFamily.Default
        )
    )
}

@AnimealPreview
@Composable
private fun AnimealTextButtonPreview() {
    AnimealTheme {
        AnimealTextButtonWithIcon(
            icon = Icons.Default.Add,
            text = "Text button",
            onClick = {}
        )
    }
}