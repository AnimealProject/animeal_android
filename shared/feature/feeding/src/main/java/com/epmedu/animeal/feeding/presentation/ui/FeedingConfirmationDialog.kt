package com.epmedu.animeal.feeding.presentation.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.button.AnimealButton
import com.epmedu.animeal.foundation.button.AnimealSecondaryButtonOutlined
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.resources.R

@Composable
fun FeedingConfirmationDialog(
    isShowing: MutableState<Boolean>,
    onAgreeClick: () -> Unit
) {
    BackHandler(enabled = isShowing.value) { isShowing.value = false }

    if (isShowing.value) {
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
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.weight(0.22f))
                Image(
                    modifier = Modifier
                        .height(100.dp)
                        .width(100.dp),
                    painter = painterResource(id = R.drawable.ic_attention),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.weight(0.22f))
                Text(
                    text = stringResource(id = R.string.willfeed_timeleft_msg),
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.weight(0.36f))
                Image(
                    modifier = Modifier
                        .fillMaxWidth(),
                    painter = painterResource(id = R.drawable.ic_will_feed_dialog),
                    contentScale = ContentScale.FillWidth,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.weight(1f))
                FeedingConfirmationButtons(
                    onCancelClick = { isShowing.value = false },
                    onAgreeClick = {
                        isShowing.value = false
                        onAgreeClick()
                    }
                )
                Spacer(modifier = Modifier.weight(0.22f))
            }
        }
    }
}

@Composable
private fun FeedingConfirmationButtons(
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
            text = stringResource(id = R.string.agree),
            onClick = onAgreeClick
        )
    }
}

@AnimealPreview
@Composable
private fun FeedConfirmationPreview() {
    val isShowing = remember { mutableStateOf(true) }

    AnimealTheme {
        FeedingConfirmationDialog(isShowing = isShowing, onAgreeClick = {})
    }
}