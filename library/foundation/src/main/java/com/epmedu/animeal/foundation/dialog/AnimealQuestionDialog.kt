package com.epmedu.animeal.foundation.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.epmedu.animeal.foundation.button.AnimealButton
import com.epmedu.animeal.foundation.button.AnimealSecondaryButtonOutlined
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.spacer.HeightSpacer
import com.epmedu.animeal.foundation.theme.AnimealTheme

@Composable
fun AnimealQuestionDialog(
    title: String,
    subtitle: String? = null,
    dismissText: String,
    acceptText: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    titleFontSize: TextUnit = 18.sp,
    onDismissRequest: () -> Unit = {},
    content: @Composable (() -> Unit)? = null,
) {
    AlertDialog(
        shape = RoundedCornerShape(30.dp),
        onDismissRequest = onDismissRequest,
        title = {
            Column {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.h6,
                    fontSize = titleFontSize
                )
                subtitle?.let { subtitle ->
                    HeightSpacer(height = 4.dp)
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.body2
                    )
                }
            }
        },
        text = content,
        buttons = {
            Row(
                modifier = Modifier.fillMaxWidth(),
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
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = true,
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
            subtitle = "Subtitle",
            dismissText = "No",
            acceptText = "Yes",
            onDismiss = {},
            onConfirm = {}
        )
    }
}