package com.epmedu.animeal.foundation.button

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.base.theme.AnimealTheme

@Suppress("UnusedPrivateMember")
@Preview
@Composable
private fun AnimealButtonPreview() {
    AnimealTheme {
        AnimealButton(
            onClick = { },
            text = "Hello World!",
        )
    }
}

@Composable
fun AnimealButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    color: Color = MaterialTheme.colors.primary,
    enabled: Boolean = true,
    text: String,
) {
    AnimealButton(
        modifier = modifier,
        color = color,
        enabled = enabled,
        onClick = onClick,
    ) {
        Text(
            text = text,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
fun AnimealButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    color: Color = MaterialTheme.colors.primary,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        modifier = modifier
            .height(56.dp)
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = color,
            contentColor = contentColorFor(
                backgroundColor = MaterialTheme.colors.primary
            ),
        ),
        shape = MaterialTheme.shapes.large,
        enabled = enabled,
        onClick = onClick,
        content = content,
    )
}
