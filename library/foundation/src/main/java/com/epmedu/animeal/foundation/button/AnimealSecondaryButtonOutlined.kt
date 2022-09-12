package com.epmedu.animeal.foundation.button

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.theme.DisabledButtonContentColor

@Composable
fun AnimealSecondaryButtonOutlined(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit,
) = OutlinedButton(
    modifier = modifier.heightIn(min = 60.dp).fillMaxWidth(),
    border = BorderStroke(1.dp, MaterialTheme.colors.primary),
    colors = ButtonDefaults.outlinedButtonColors(
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
    onClick = onClick
) {
    Text(
        text = text,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        color = MaterialTheme.colors.primary,
    )
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AnimealSecondaryButtonPreview() {
    AnimealTheme {
        AnimealSecondaryButtonOutlined(
            text = "Button",
            onClick = {},
        )
    }
}