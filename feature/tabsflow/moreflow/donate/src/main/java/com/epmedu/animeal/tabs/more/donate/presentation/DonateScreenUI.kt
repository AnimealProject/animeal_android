package com.epmedu.animeal.tabs.more.donate.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
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
    val horizontalPadding = 26.dp
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        DonateHeading(onBack = onBack)
        HeightSpacer(height = 16.dp)
        Image(
            modifier = Modifier
                .padding(horizontal = horizontalPadding)
                .clip(RoundedCornerShape(12.dp))
                .aspectRatio(2.0f),
            painter = painterResource(id = R.drawable.doggo),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        HeightSpacer(height = 16.dp)
        Text(
            text = stringResource(id = R.string.donation_thanks),
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = horizontalPadding),
        )
        HeightSpacer(height = 16.dp)
        when {
            state.isLoading -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
            else -> {
                LazyColumn(
                    verticalArrangement = Arrangement.LastElementBottom,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    items(state.donationInformation) { donateInformation ->
                        DonateListItem(
                            modifier = Modifier.padding(horizontal = horizontalPadding),
                            donateInformation = donateInformation,
                        )
                        HeightSpacer(height = 16.dp)
                    }
                }
            }
        }
    }
}

@AnimealPreview
@Composable
private fun DonateScreenPreview() {
    AnimealTheme {
        DonateScreenUI(
            onBack = {},
            state = DonateState(donationInformation = donateInformationStubList, isLoading = false)
        )
    }
}

@AnimealPreview
@Composable
private fun DonateScreenLoadingStatePreview() {
    AnimealTheme {
        DonateScreenUI(
            onBack = {},
            state = DonateState()
        )
    }
}
