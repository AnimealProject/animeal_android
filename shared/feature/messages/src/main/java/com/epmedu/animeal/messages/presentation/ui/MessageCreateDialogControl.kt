package com.epmedu.animeal.messages.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.extensions.testTagAsResourceId
import com.epmedu.animeal.foundation.button.AnimealButton
import com.epmedu.animeal.foundation.button.AnimealSecondaryButtonOutlined
import com.epmedu.animeal.foundation.input.TextInputField
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.theme.CustomColor.Porcelain
import com.epmedu.animeal.messages.presentation.event.MessageCreateDialogEvent
import com.epmedu.animeal.messages.presentation.event.MessageCreateDialogEvent.BodyChanged
import com.epmedu.animeal.messages.presentation.event.MessageCreateDialogEvent.BodyFocusCleared
import com.epmedu.animeal.messages.presentation.event.MessageCreateDialogEvent.Close
import com.epmedu.animeal.messages.presentation.event.MessageCreateDialogEvent.Confirm
import com.epmedu.animeal.messages.presentation.model.FeedingPointModel
import com.epmedu.animeal.messages.presentation.viewmodel.MessageCreateDialogState
import com.epmedu.animeal.resources.R

@Composable
@Suppress("LongMethod", "CyclomaticComplexMethod")
internal fun MessageCreateDialogControl(
    feedingPoint: FeedingPointModel,
    state: MessageCreateDialogState,
    onEvent: (MessageCreateDialogEvent) -> Unit,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.secondaryVariant)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(padding)
                .padding(horizontal = 24.dp),
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(imageVector = Icons.Default.ChatBubbleOutline, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Report an Issue",
                    style = MaterialTheme.typography.h6
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Feeding point",
                modifier = Modifier.padding(bottom = 2.dp),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.subtitle2
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 1.dp, color = Porcelain, shape = RoundedCornerShape(12.dp))
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(12.dp)
                    ),
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Text(
                        text = feedingPoint.code,
                        style = MaterialTheme.typography.subtitle2,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colors.onSurface,
                        maxLines = 1
                    )
                    Text(
                        text = feedingPoint.title,
                        style = MaterialTheme.typography.subtitle2,
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colors.onSurface,
                        maxLines = 1
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            TextInputField(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .testTagAsResourceId("message_body"),
                isEnabled = true,
                title = stringResource(id = R.string.message_body_title),
                hint = stringResource(id = R.string.message_body_hint),
                onValueChange = { onEvent(BodyChanged(it)) },
                onClearFocus = { onEvent(BodyFocusCleared) },
                value = state.body,
                errorText = state.bodyError.asString(),
                multiLines = true
            )
            MessageCreateDialogButtons(
                onCancelClick = { onEvent(Close) },
                onAgreeClick = { onEvent(Confirm) }
            )
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
private fun MessageCreateDialogButtons(
    onCancelClick: () -> Unit,
    onAgreeClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AnimealSecondaryButtonOutlined(
            modifier = Modifier.weight(1F),
            text = stringResource(id = R.string.cancel),
            onClick = onCancelClick
        )
        AnimealButton(
            modifier = Modifier.weight(1F),
            text = stringResource(id = R.string.send),
            onClick = onAgreeClick
        )
    }
}

@AnimealPreview
@Composable
private fun MessageCreateDialogControlPreview() {
    AnimealTheme {
        MessageCreateDialogControl(
            feedingPoint = FeedingPointModel("1212-1212", "Super name for FP"),
            state = MessageCreateDialogState(),
            onEvent = {}
        )
    }
}