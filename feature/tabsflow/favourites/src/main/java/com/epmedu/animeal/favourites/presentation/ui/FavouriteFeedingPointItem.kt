package com.epmedu.animeal.favourites.presentation.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.foundation.button.AnimealHeartButton
import com.epmedu.animeal.foundation.common.FeedStatus
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.theme.CustomColor

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FavouriteFeedingPointItem(
    title: String,
    status: FeedStatus,
    isFavourite: Boolean,
    onFavouriteChange: (Boolean) -> Unit,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(
            width = 1.dp,
            color = if (isSystemInDarkTheme()) Color.Black else CustomColor.Porcelain
        ),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(
                        color = MaterialTheme.colors.secondaryVariant,
                        shape = RoundedCornerShape(10.dp)
                    )
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(vertical = 8.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colors.onSurface,
                    maxLines = 2,
                )
                FeedStatus(
                    status = status,
                )
            }
            AnimealHeartButton(
                modifier = Modifier.align(Alignment.Top),
                selected = isFavourite,
                onChange = onFavouriteChange,
            )
        }
    }
}

@AnimealPreview
@Composable
fun MoreScreenPreview() {
    val longText = "Very very very very very very very very very long text"
    val shortText = "Short text"

    AnimealTheme {
        Column {
            FavouriteFeedingPointItem(
                longText,
                FeedStatus.RED,
                isFavourite = true,
                {}
            ) {}
            FavouriteFeedingPointItem(
                shortText,
                FeedStatus.GREEN,
                isFavourite = false,
                {}
            ) {}
        }
    }
}
