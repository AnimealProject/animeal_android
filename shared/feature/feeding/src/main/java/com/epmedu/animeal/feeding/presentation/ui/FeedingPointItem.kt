package com.epmedu.animeal.feeding.presentation.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.common.constants.DefaultConstants.EMPTY_STRING
import com.epmedu.animeal.feeding.presentation.model.FeedStatus
import com.epmedu.animeal.foundation.button.AnimealHeartButton
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.theme.CustomColor
import com.epmedu.animeal.networkstorage.domain.NetworkFile

@OptIn(ExperimentalMaterialApi::class)
@Suppress("LongMethod")
@Composable
fun FeedingPointItem(
    code: String,
    title: String,
    inactive: Boolean,
    status: FeedStatus,
    isFavourite: Boolean,
    image: NetworkFile?,
    onFavouriteChange: (Boolean) -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box {
        Card(
            modifier = modifier
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
                    .padding(12.dp)
                    .alpha(if (inactive) 0.6f else 1.0f),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                FeedingPointImage(
                    image = image,
                    contentDescription = title
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(bottom = 8.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = code,
                            style = MaterialTheme.typography.subtitle2,
                            overflow = TextOverflow.Ellipsis,
                            color = MaterialTheme.colors.onSurface,
                            maxLines = 1
                        )
                        Text(
                            text = title,
                            style = MaterialTheme.typography.subtitle1,
                            fontWeight = FontWeight.Bold,
                            overflow = TextOverflow.Ellipsis,
                            color = MaterialTheme.colors.onSurface,
                            maxLines = 1
                        )
                    }
                    FeedStatusItem(
                        status = status,
                    )
                }
            }
        }

        AnimealHeartButton(
            modifier = Modifier.align(Alignment.TopEnd).padding(4.dp),
            selected = isFavourite,
            onChange = onFavouriteChange,
        )

        if (inactive) {
            FeedingPointInactiveBadge()
        }
    }
}

@AnimealPreview
@Composable
fun MoreScreenPreview() {
    val code = "GE-TB-0000-D-0000"
    val longText = "Very very very very very Very Very Very very long text"
    val shortText = "Short text"
    val image = NetworkFile(
        name = EMPTY_STRING,
        url = "https://fastly.picsum.photos/id/237/200/300.jpg?" +
            "hmac=TmmQSbShHz9CdQm0NkEjx1Dyh_Y984R9LpNrpvH2D_U"
    )

    AnimealTheme {
        Column {
            FeedingPointItem(
                code,
                longText,
                true,
                FeedStatus.Starved,
                isFavourite = true,
                image,
                {},
                {}
            )
            FeedingPointItem(
                code,
                shortText,
                false,
                FeedStatus.Fed,
                isFavourite = false,
                image,
                {},
                {}
            )
        }
    }
}
