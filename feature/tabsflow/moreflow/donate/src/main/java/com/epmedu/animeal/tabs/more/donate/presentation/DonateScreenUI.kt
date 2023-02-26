package com.epmedu.animeal.tabs.more.donate.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
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
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.topbar.BackButton
import com.epmedu.animeal.foundation.topbar.TopBar
import com.epmedu.animeal.resources.R
import com.epmedu.animeal.tabs.more.donate.domain.DonateInformation
import com.epmedu.animeal.tabs.more.donate.presentation.viewmodel.DonateState
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun DonateScreenUI(
    onEvent: (event: DonateScreenEvent) -> Unit,
    onBack: () -> Unit,
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
                BackButton { onBack() }
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
            modifier = Modifier.padding(start = 26.dp, top = 10.dp, end = 26.dp, bottom = 21.dp),
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
                    paymentCredentials = donationInfo.paymentCredentials,
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
            onBack = {},
            state = DonateState(
                donationInformation = persistentListOf(
                    DonateInformation(
                        title = "TBC Bank",
                        paymentCredentials = "2GE82983752093855555",
                        icon = R.drawable.ic_tbc_bank,
                    ),
                    DonateInformation(
                        title = "PayPal",
                        paymentCredentials = "donate.me@example.com",
                        icon = R.drawable.ic_paypal,
                    ),
                )
            )
        )
    }
}
