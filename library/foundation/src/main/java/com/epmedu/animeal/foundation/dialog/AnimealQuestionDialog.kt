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
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.epmedu.animeal.foundation.button.AnimealButton
import com.epmedu.animeal.foundation.button.AnimealSecondaryButtonOutlined
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme

@Composable
fun AnimealQuestionDialog(
    title: String,
    dismissText: String? = null,
    acceptText: String? = null,
    onDismissRequest: () -> Unit = {},
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    content: @Composable (() -> Unit)? = null,
) {
    AlertDialog(
        modifier = Modifier.padding(36.dp),
        shape = RoundedCornerShape(30.dp),
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.h6,
                fontSize = 18.sp
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
                dismissText?.let {
                    AnimealSecondaryButtonOutlined(
                        modifier = Modifier.weight(1f),
                        text = it,
                        onClick = {
                            onDismiss()
                        },
                    )
                }
                acceptText?.let {
                    AnimealButton(
                        modifier = Modifier.weight(1f),
                        text = acceptText,
                        onClick = {
                            onConfirm()
                        },
                    )
                }
            }
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    )
}

@AnimealPreview
@Composable
private fun AnimealAlertQuestionPreview() {
    AnimealTheme {
        AnimealQuestionDialog(
            title = "Title",
            dismissText = "No",
            acceptText = "Yes",
            onDismiss = {},
            onConfirm = {}
        )
    }
}

@AnimealPreview
@Composable
private fun AnimealAlertQuestionSingleOptionPreview() {
    AnimealTheme {
        AnimealQuestionDialog(
            title = "One Option",
            dismissText = "Accept",
            onDismiss = {},
            onConfirm = {}
        )
    }
}