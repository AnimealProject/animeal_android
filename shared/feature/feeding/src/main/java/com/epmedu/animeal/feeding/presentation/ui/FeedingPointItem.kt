package com.epmedu.animeal.feeding.presentation.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.epmedu.animeal.feeding.presentation.model.FeedStatus
import com.epmedu.animeal.foundation.button.AnimealHeartButton
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.theme.CustomColor

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FeedingPointItem(
    title: String,
    status: FeedStatus,
    isFavourite: Boolean,
    imageUrl: String,
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
            Card(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(16.dp)),
                elevation = 8.dp
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageUrl)
                        .crossfade(true)
                        .build(),
                    contentScale = ContentScale.Crop,
                    contentDescription = title,
                )
            }
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
                FeedStatusItem(
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

@Suppress("LongMethod")
@AnimealPreview
@Composable
fun MoreScreenPreview() {
    val longText = "Very very very very very very very very very long text"
    val shortText = "Short text"
    val imgUrl = "https://fastly.picsum.photos/id/866/200/300.jpg?" +
            "hmac=rcadCENKh4rD6MAp6V_ma-AyWv641M4iiOpe1RyFHeI"

    AnimealTheme {
        Column {
            FeedingPointItem(
                longText,
                FeedStatus.RED,
                isFavourite = true,
                imgUrl,
                {}
            ) {}
            FeedingPointItem(
                shortText,
                FeedStatus.GREEN,
                isFavourite = false,
                imgUrl,
                {}
            ) {}
        }
    }
}
