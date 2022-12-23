package com.epmedu.animeal.tabs.more.donate.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.extensions.copyText
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.topbar.BackButton
import com.epmedu.animeal.foundation.topbar.TopBar
import com.epmedu.animeal.navigation.navigator.LocalNavigator
import com.epmedu.animeal.resources.R
import com.epmedu.animeal.tabs.more.donate.domain.DonateInformation
import com.epmedu.animeal.tabs.more.donate.presentation.viewmodel.DonateState

@Composable
internal fun DonateScreenUI(
    onEvent: (event: DonateScreenEvent) -> Unit,
    state: DonateState,
) {
    onState(state, onEvent)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(
                title = stringResource(id = R.string.page_donate),
                modifier = Modifier.statusBarsPadding()
            ) {
                BackButton { onEvent(DonateScreenEvent.BackClicked) }
            }
        },
        content = { Content(state, onEvent) }
    )
}

@Composable
private fun Content(
    state: DonateState,
    onEvent: (event: DonateScreenEvent) -> Unit
) {
    Column {
        Text(
            modifier = Modifier.padding(26.dp, 10.dp, 26.dp, 21.dp),
            style = MaterialTheme.typography.subtitle1,
            text = stringResource(R.string.donation_subtitle),
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(vertical = 21.dp)
        ) {
            item {
                Image(
                    modifier = Modifier
                        .padding(37.dp, 12.dp, 37.dp, 12.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .aspectRatio(2.0f),
                    painter = painterResource(id = R.drawable.doggo),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
            items(state.donationInformation) { donationInfo: DonateInformation ->
                DonateListItem(
                    Modifier.padding(horizontal = 26.dp),
                    icon = donationInfo.icon,
                    header = donationInfo.title,
                    bankNumber = donationInfo.number,
                    onEvent = onEvent,
                )
            }
            item {
                Text(
                    text = stringResource(id = R.string.donation_thanks),
                    style = MaterialTheme.typography.body1,
                )
            }
        }
    }
}

@Composable
private fun onState(
    state: DonateState,
    onEvent: (event: DonateScreenEvent) -> Unit
) {
    if (state.popBackstack) {
        LocalNavigator.currentOrThrow.popBackStack()
    }
    state.donationNumberToCopy?.let { number ->
        LocalContext.current.copyText(
            text = number,
            toastText = R.string.donation_copy_toast
        )
        onEvent(DonateScreenEvent.NumberIsCopied)
    }
}

@AnimealPreview
@Composable
private fun DonateScreenPreview() {
    AnimealTheme {
        DonateScreenUI(
            onEvent = {},
            state = DonateState()
        )
    }
}
