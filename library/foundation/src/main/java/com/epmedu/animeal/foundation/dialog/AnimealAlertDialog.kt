package com.epmedu.animeal.foundation.dialog

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epmedu.animeal.foundation.button.AnimealButton
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme

@Composable
fun AnimealAlertDialog(
    title: String,
    acceptText: String,
    onConfirm: () -> Unit,
    titleFontSize: TextUnit = 18.sp,
    content: @Composable (() -> Unit)? = null,
) {
    AlertDialog(
        shape = RoundedCornerShape(30.dp),
        onDismissRequest = onConfirm,
        title = {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = titleFontSize,
                style = MaterialTheme.typography.h6
            )
        },
        text = content,
        buttons = {
            AnimealButton(
                text = acceptText,
                onClick = onConfirm
            )
        }
    )
}

@AnimealPreview
@Composable
private fun AnimealAlertDialogPreview() {
    AnimealTheme {
        AnimealAlertDialog(
            title = "Title text",
            acceptText = "Accept text",
            onConfirm = {}
        )
    }
}