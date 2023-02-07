package com.epmedu.animeal.home.presentation.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epmedu.animeal.foundation.button.AnimealButton
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.resources.R

@Composable
internal fun FeedingExpiredDialog(
    onConfirm: () -> Unit,
    content: @Composable (() -> Unit)? = null,
) {
    AlertDialog(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(30.dp),
        onDismissRequest = onConfirm,
        title = {
            Text(
                text = stringResource(id = R.string.feeding_timer_expired),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                style = MaterialTheme.typography.h6
            )
        },
        text = content,
        buttons = {
            AnimealButton(
                modifier = Modifier.fillMaxWidth().padding(24.dp),
                text = stringResource(id = R.string.got_it),
                onClick = { onConfirm() }

            )
        }
    )
}

@AnimealPreview
@Composable
private fun FeedingExpiredDialogPreview() {
    AnimealTheme {
        FeedingExpiredDialog(
            onConfirm = {},
            content = null
        )
    }
}