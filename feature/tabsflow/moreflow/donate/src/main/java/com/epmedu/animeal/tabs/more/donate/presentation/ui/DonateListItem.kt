package com.epmedu.animeal.tabs.more.donate.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.epmedu.animeal.extensions.copyText
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.spacer.HeightSpacer
import com.epmedu.animeal.foundation.spacer.WidthSpacer
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.theme.CustomColor
import com.epmedu.animeal.resources.R
import com.epmedu.animeal.tabs.more.donate.domain.DonateInformation
import com.epmedu.animeal.tabs.more.donate.util.tbcBankInformation

@Composable
internal fun DonateListItem(
    donateInformation: DonateInformation,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    Column(modifier = modifier) {
        Text(
            text = donateInformation.title,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.subtitle1
        )
        HeightSpacer(2.dp)
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(color = backgroundColor())
                .clickable {
                    context.copyText(
                        text = donateInformation.paymentCredentials,
                        toastText = R.string.donation_copy_toast
                    )
                }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(donateInformation.iconUrl)
                    .build(),
                modifier = Modifier.size(26.dp),
                contentDescription = donateInformation.title
            )
            WidthSpacer(width = 16.dp)
            Text(
                modifier = Modifier.weight(1.0f),
                style = MaterialTheme.typography.body1,
                text = donateInformation.paymentCredentials,
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
private fun backgroundColor(): Color {
    return if (isSystemInDarkTheme()) CustomColor.DarkestGrey else CustomColor.LynxWhite
}

@AnimealPreview
@Composable
private fun DonateListItemPreview() {
    AnimealTheme {
        DonateListItem(
            donateInformation = tbcBankInformation,
        )
    }
}