package com.epmedu.animeal.foundation.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.theme.DisabledButtonColor
import com.epmedu.animeal.foundation.theme.DisabledButtonContentColor

@Composable
fun AnimealSecondaryButtonOutlined(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val primaryColor = if (enabled) MaterialTheme.colors.primary else DisabledButtonColor
    OutlinedButton(
        modifier = modifier
            .heightIn(min = 60.dp)
            .fillMaxWidth(),
        border = BorderStroke(
            width = 1.dp,
            color = primaryColor
        ),
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
            color = primaryColor,
            style = TextStyle(letterSpacing = 1.sp, fontWeight = FontWeight.Bold)
        )
    }
}

@AnimealPreview
@Composable
private fun AnimealSecondaryButtonPreview() {
    AnimealTheme {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            AnimealSecondaryButtonOutlined(
                text = "Button",
                onClick = {},
            )
            Divider()
            AnimealSecondaryButtonOutlined(
                text = "Disabled button",
                onClick = { },
                enabled = false
            )
        }
    }
}