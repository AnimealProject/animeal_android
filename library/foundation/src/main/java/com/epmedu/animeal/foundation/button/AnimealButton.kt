package com.epmedu.animeal.foundation.button

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.base.theme.AnimealTheme
import com.epmedu.animeal.base.theme.DisabledButtonColor
import com.epmedu.animeal.base.theme.DisabledButtonContentColor
import com.epmedu.animeal.foundation.spacer.HeightSpacer

@Composable
fun AnimealButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    AnimealButton(
        modifier = modifier,
        enabled = enabled,
        onClick = onClick
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
        shape = MaterialTheme.shapes.large,
        enabled = enabled,
        onClick = onClick,
        content = content,
    )
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun AnimealButtonPreview() {
    AnimealTheme {
        Surface {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                AnimealButton(
                    onClick = { },
                    text = "Hello World!",
                )
                AnimealButton(
                    enabled = false,
                    onClick = { },
                    text = "Hello World!",
                )

                HeightSpacer(height = 8.dp)

                AnimealButton(onClick = { }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                    Text(text = "Hello World!")
                }
                AnimealButton(
                    enabled = false,
                    onClick = { },
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                    Text(text = "Hello World!")
                }
            }
        }
    }
}
