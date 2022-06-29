package com.epmedu.animeal.base.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.base.theme.AnimealTheme

@Composable
fun ShortButton(
    text: String,
    padding: PaddingValues = PaddingValues(),
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(padding)
            .size(width = 208.dp, height = 60.dp),
        enabled = enabled,
        shape = RoundedCornerShape(30.dp),
        colors = ButtonDefaults.buttonColors(disabledContentColor = Color.White)
    ) {
        Text(text = text)
    }
}

@Preview
@Composable
fun ShortButtonPreview() {
    AnimealTheme {
        Surface {
            ShortButton(text = "Click", onClick = {})
        }
    }
}