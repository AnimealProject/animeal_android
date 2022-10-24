package com.epmedu.animeal.feedconfirmation.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.feedconfirmation.R
import com.epmedu.animeal.foundation.button.AnimealButton
import com.epmedu.animeal.foundation.button.AnimealSecondaryButton
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme

@Composable
internal fun FeedConfirmationUI(
    onAgreeClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
            .background(color = MaterialTheme.colors.secondaryVariant)
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp),
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
                text = stringResource(id = com.epmedu.animeal.resources.R.string.willfeed_timeleft_msg),
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Bold
            )
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 44.dp),
                painter = painterResource(id = R.mipmap.img_confirm_feeding),
                contentScale = ContentScale.FillWidth,
                contentDescription = null
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AnimealSecondaryButton(
                    modifier = Modifier.weight(1F),
                    text = stringResource(id = com.epmedu.animeal.resources.R.string.cancel),
                    onClick = onCancelClick
                )
                AnimealButton(
                    modifier = Modifier.weight(1F),
                    text = stringResource(id = com.epmedu.animeal.resources.R.string.agree),
                    onClick = onAgreeClick
                )
            }
        }
    }
}

@AnimealPreview
@Composable
private fun FeedConfirmationPreview() {
    AnimealTheme {
        FeedConfirmationUI({}, {})
    }
}