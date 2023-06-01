package com.epmedu.animeal.tabs.more.donate.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.layout.LastElementBottom
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.spacer.HeightSpacer
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.resources.R
import com.epmedu.animeal.tabs.more.donate.presentation.ui.DonateHeading
import com.epmedu.animeal.tabs.more.donate.presentation.ui.DonateListItem
import com.epmedu.animeal.tabs.more.donate.presentation.viewmodel.DonateState
import com.epmedu.animeal.tabs.more.donate.util.donateInformationStubList

@Composable
internal fun DonateScreenUI(
    state: DonateState,
    onBack: () -> Unit,
) {
    LazyColumn(
        verticalArrangement = Arrangement.LastElementBottom,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            DonateHeading(onBack = onBack)
            HeightSpacer(height = 43.dp)
        }
        items(state.donationInformation) { donateInformation ->
            DonateListItem(
                modifier = Modifier.padding(horizontal = 26.dp),
                donateInformation = donateInformation,
            )
            HeightSpacer(height = 32.dp)
        }
        item {
            Text(
                text = stringResource(id = R.string.donation_thanks),
                style = MaterialTheme.typography.body1,
            )
            HeightSpacer(height = 40.dp)
        }
    }
}

@AnimealPreview
@Composable
private fun DonateScreenPreview() {
    AnimealTheme {
        DonateScreenUI(
            onBack = {},
            state = DonateState(donationInformation = donateInformationStubList)
        )
    }
}
