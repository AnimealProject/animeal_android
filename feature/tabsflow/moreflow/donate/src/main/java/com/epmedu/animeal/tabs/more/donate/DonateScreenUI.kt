package com.epmedu.animeal.tabs.more.donate

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.extensions.copyText
import com.epmedu.animeal.extensions.currentOrThrow
import com.epmedu.animeal.foundation.placeholder.ScreenPlaceholder
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.theme.CustomColor
import com.epmedu.animeal.navigation.navigator.LocalNavigator
import com.epmedu.animeal.resources.R
import com.epmedu.animeal.tabs.more.donate.viewmodel.DonateState

@Composable
internal fun DonateScreenUI(
    onEvent: (event: DonateScreenEvent) -> Unit,
    state: DonateState,
) {

    onState(state, onEvent)

    ScreenPlaceholder(
        title = stringResource(id = R.string.page_donate),
        onBack = { onEvent(DonateScreenEvent.BackClicked) },
        content = {
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
                    item {
                        DonateListItem(
                            Modifier.padding(horizontal = 26.dp),
                            icon = R.drawable.ic_bank_of_georgia,
                            header = "Bank of Georgia",
                            bankNumber = "1GE82983752093855555",
                            onEvent,
                        )
                    }
                    item {
                        DonateListItem(
                            Modifier.padding(horizontal = 26.dp),
                            icon = R.drawable.ic_tbc_bank,
                            header = "TBC Bank",
                            bankNumber = "2GE82983752093855555",
                            onEvent,
                        )
                    }
                    item {
                        DonateListItem(
                            Modifier.padding(horizontal = 26.dp),
                            icon = R.drawable.ic_paypal,
                            header = "PayPal",
                            bankNumber = "3GE82983752093855555",
                            onEvent,
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
    )
}

@Composable
internal fun DonateListItem(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    header: String,
    bankNumber: String,
    onEvent: (event: DonateScreenEvent) -> Unit,
) {
    Column(modifier = modifier) {
        Text(
            text = header,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.subtitle1
        )
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(color = CustomColor.LynxWhite)
                .clickable {
                    onEvent(DonateScreenEvent.DonateNumberClicked(bankNumber))
                }
                .padding(16.dp)
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = Color.Unspecified,
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                modifier = Modifier.weight(1.0f),
                style = MaterialTheme.typography.body1,
                text = bankNumber,
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_copy),
                contentDescription = null,
                tint = Color.Unspecified,
            )
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
