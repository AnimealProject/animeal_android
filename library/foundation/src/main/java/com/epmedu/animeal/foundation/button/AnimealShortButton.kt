package com.epmedu.animeal.foundation.button

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.base.theme.AnimealTheme
import com.epmedu.animeal.base.theme.Gainsboro

@Composable
fun AnimealShortButton(
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
            disabledBackgroundColor = Gainsboro,
            disabledContentColor = Color.White
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
private fun ShortButtonPreview() {
    AnimealTheme {
        Surface {
            Column {
                AnimealShortButton(text = "Enabled", onClick = {})
                Divider()
                AnimealShortButton(text = "Disabled", enabled = false, onClick = {})
            }
        }
    }
}