package com.epmedu.animeal.feedconfirmation.presentation

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.epmedu.animeal.feedconfirmation.viewmodel.FeedConfirmationViewModel
import com.epmedu.animeal.foundation.button.AnimealButton
import com.epmedu.animeal.foundation.button.AnimealSecondaryButtonOutlined
import com.epmedu.animeal.foundation.dialog.AnimealQuestionDialog
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.resources.R

@Composable
fun FeedConfirmationDialog(
    feedingPointId: String,
    onCancelClick: () -> Unit
) {

    val viewModel = hiltViewModel<FeedConfirmationViewModel>()
    val state by viewModel.stateFlow.collectAsState()

    val showFeedingPointExpired = state.showBookingException

    if (showFeedingPointExpired) {
        AnimealQuestionDialog(
            title = stringResource(R.string.feeding_point_expired_description),
            acceptText = stringResource(R.string.feeding_point_expired_accept),
            onDismiss = {},
            onConfirm = onCancelClick )
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.secondaryVariant)
    ) { padding ->
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .padding(horizontal = 24.dp, vertical = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp),
                painter = painterResource(id = R.drawable.ic_attention),
                contentDescription = null
            )
            Text(
                modifier = Modifier.padding(top = 36.dp),
                text = stringResource(id = R.string.willfeed_timeleft_msg),
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Bold
            )
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 44.dp),
                painter = painterResource(id = R.drawable.ic_will_feed_dialog),
                contentScale = ContentScale.FillWidth,
                contentDescription = null
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier.fillMaxWidth(),
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
                    onClick = {
                        viewModel.handleEvents(FeedConfirmationEvent.AcceptFeedDialog(feedingPointId))
                    }
                )
            }
        }
    }
}

@AnimealPreview
@Composable
private fun FeedConfirmationPreview() {
    AnimealTheme {
        FeedConfirmationDialog("", {})
    }
}