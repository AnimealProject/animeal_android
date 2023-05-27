package com.epmedu.animeal.foundation.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.spacer.HeightSpacer
import com.epmedu.animeal.foundation.text.AutoSizeText
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.theme.DisabledButtonColor
import com.epmedu.animeal.foundation.theme.DisabledButtonContentColor

@Composable
fun AnimealButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colors.primary,
    contentColor: Color = contentColorFor(backgroundColor),
    enabled: Boolean = true,
) {
    AnimealButton(
        modifier = modifier,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        enabled = enabled,
        onClick = onClick
    ) {
        AutoSizeText(
            textStyle = TextStyle(letterSpacing = 1.sp),
            text = text,
            textSize = 16.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
fun AnimealButton(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colors.primary,
    contentColor: Color = contentColorFor(backgroundColor),
    enabled: Boolean = true,
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        modifier = modifier
            .height(60.dp)
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
            contentColor = contentColor,
            disabledBackgroundColor = DisabledButtonColor,
            disabledContentColor = DisabledButtonContentColor
        ),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            disabledElevation = 0.dp,
            hoveredElevation = 0.dp,
            focusedElevation = 0.dp,
        ),
        shape = MaterialTheme.shapes.large,
        enabled = enabled,
        onClick = onClick,
        content = content,
    )
}

@AnimealPreview
@Composable
private fun AnimealButtonPreview() {
    val text = "Hello World!"
    AnimealTheme {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            AnimealButton(
                onClick = { },
                text = text,
            )
            AnimealButton(
                enabled = false,
                onClick = { },
                text = text,
            )

            HeightSpacer(height = 8.dp)

            AnimealButton(onClick = { }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
                Text(text = text)
            }
            AnimealButton(
                enabled = false,
                onClick = { },
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
                Text(text = text)
            }
        }
    }
}
