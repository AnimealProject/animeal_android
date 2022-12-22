package com.epmedu.animeal.tabs.more.donate.presentation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import com.epmedu.animeal.foundation.theme.AnimealIcon
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.theme.CustomColor.secondaryBackground
import com.epmedu.animeal.resources.R

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
                .background(color = secondaryBackground)
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
                painter = AnimealIcon.copyIcon,
                contentDescription = null,
                tint = Color.Unspecified,
            )
        }
    }
}

@AnimealPreview
@Composable
fun DonateListItemPreview() {
    AnimealTheme {
        DonateListItem(
            icon = R.drawable.ic_bank_of_georgia,
            header = "Bank of Georgia",
            bankNumber = "1GE82983752093855555",
            onEvent = {},
        )
    }
}