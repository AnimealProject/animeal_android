package com.epmedu.animeal.tabs.more.donate.presentation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.spacer.HeightSpacer
import com.epmedu.animeal.foundation.spacer.WidthSpacer
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.theme.CustomColor.secondaryBackground
import com.epmedu.animeal.resources.R

@Composable
internal fun DonateListItem(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    header: String,
    paymentCredentials: String,
    onEvent: (event: DonateScreenEvent) -> Unit,
) {
    Column(modifier = modifier) {
        Text(
            text = header,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.subtitle1
        )
        HeightSpacer(2.dp)
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(color = secondaryBackground)
                .clickable {
                    onEvent(DonateScreenEvent.DonateNumberClicked(paymentCredentials))
                }
                .padding(16.dp)
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = Color.Unspecified,
            )
            WidthSpacer(width = 16.dp)
            Text(
                modifier = Modifier.weight(1.0f),
                style = MaterialTheme.typography.body1,
                text = paymentCredentials,
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_copy),
                contentDescription = null,
                tint = Color.Unspecified,
            )
        }
    }
}

@AnimealPreview
@Composable
private fun DonateListItemPreview() {
    AnimealTheme {
        DonateListItem(
            icon = R.drawable.ic_bank_of_georgia,
            header = "Bank of Georgia",
            paymentCredentials = "1GE82983752093855555",
            onEvent = {},
        )
    }
}