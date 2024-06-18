package com.epmedu.animeal.feedings.presentation.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.common.constants.DefaultConstants.EMPTY_STRING
import com.epmedu.animeal.feeding.presentation.ui.FeedingPointImage
import com.epmedu.animeal.feedings.presentation.model.FeedingModel
import com.epmedu.animeal.feedings.presentation.model.FeedingModelStatus
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.theme.CustomColor
import com.epmedu.animeal.networkstorage.domain.NetworkFile

@Composable
internal fun FeedingItem(
    feedingModel: FeedingModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(
            width = 1.dp,
            color = if (isSystemInDarkTheme()) Color.Black else CustomColor.Porcelain
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            FeedingPointImage(
                image = feedingModel.image,
                contentDescription = feedingModel.title
            )
            FeedingItemDetails(feedingModel)
        }
    }
}

@AnimealPreview
@Composable
fun FeedingItemPreview() {
    val longText = "Very very very very very very very very very long text"
    val shortText = "Short text"
    val image = NetworkFile(
        name = EMPTY_STRING,
        url = "https://fastly.picsum.photos/id/866/200/300.jpg?" +
            "hmac=rcadCENKh4rD6MAp6V_ma-AyWv641M4iiOpe1RyFHeI"
    )

    AnimealTheme {
        Column {
            FeedingModelStatus.values().forEachIndexed { index, status ->
                FeedingItem(
                    feedingModel = FeedingModel(
                        id = "$index",
                        feedingPointId = "$index",
                        title = setOf(longText, shortText).elementAt(index % 2),
                        feeder = "Giorgi Abutibze",
                        status = status,
                        elapsedTime = "12 hours ago",
                        image = image
                    ),
                    onClick = {}
                )
            }
        }
    }
}