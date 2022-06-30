package com.epmedu.animeal.base.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.base.theme.AnimealTheme
import com.epmedu.animeal.base.theme.DisabledButtonColor
import com.epmedu.animeal.base.theme.DisabledButtonContentColor

@Composable
fun ShortButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        modifier = modifier.size(width = 208.dp, height = 60.dp),
        enabled = enabled,
        shape = RoundedCornerShape(30.dp),
        colors = ButtonDefaults.buttonColors(
            disabledBackgroundColor = DisabledButtonColor,
            disabledContentColor = DisabledButtonContentColor
        )
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview
@Composable
fun ShortButtonPreview() {
    AnimealTheme {
        Surface {
            Column {
                ShortButton(text = "Enabled", onClick = {})
                Divider()
                ShortButton(text = "Disabled", enabled = false, onClick = {})
            }
        }
    }
}