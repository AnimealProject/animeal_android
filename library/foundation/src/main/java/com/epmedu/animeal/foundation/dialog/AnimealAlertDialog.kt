package com.epmedu.animeal.foundation.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.button.AnimealButton
import com.epmedu.animeal.foundation.button.AnimealSecondaryButtonOutlined
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme

@Suppress("LongParameterList")
@Composable
fun AnimealAlertDialog(
    title: String,
    dismissText: String,
    acceptText: String,
    onDismissRequest: () -> Unit = {},
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    content: @Composable (() -> Unit)? = null,
) {
    AlertDialog(
        modifier = Modifier.padding(24.dp),
        shape = RoundedCornerShape(30.dp),
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.h6
            )
        },
        text = content,
        buttons = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                AnimealSecondaryButtonOutlined(
                    modifier = Modifier.weight(1f),
                    text = dismissText,
                    onClick = {
                        onDismiss()
                    },
                )
                AnimealButton(
                    modifier = Modifier.weight(1f),
                    text = acceptText,
                    onClick = {
                        onConfirm()
                    },
                )
            }
        }
    )
}

@AnimealPreview
@Composable
private fun AnimealAlertDialogPreview() {
    AnimealTheme {
        AnimealAlertDialog(
            title = "Title",
            dismissText = "No",
            acceptText = "Yes",
            onDismiss = {},
            onConfirm = {}
        )
    }
}