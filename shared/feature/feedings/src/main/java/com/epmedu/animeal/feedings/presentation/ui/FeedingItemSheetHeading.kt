package com.epmedu.animeal.feedings.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.feeding.presentation.ui.FeedingPointImage
import com.epmedu.animeal.feedings.presentation.model.FeedingModel

@Composable
internal fun FeedingItemSheetHeading(feeding: FeedingModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(horizontal = 30.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        FeedingPointImage(
            image = feeding.image,
            contentDescription = feeding.title
        )
        FeedingItemDetails(feeding)
    }
}