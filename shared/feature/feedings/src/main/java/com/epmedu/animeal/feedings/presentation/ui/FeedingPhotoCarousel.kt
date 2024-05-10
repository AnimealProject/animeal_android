package com.epmedu.animeal.feedings.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.epmedu.animeal.feedings.presentation.model.FeedingModel
import com.epmedu.animeal.foundation.spacer.WidthSpacer

@Composable
internal fun FeedingPhotoCarousel(
    feeding: FeedingModel,
    contentAlpha: Float,
    onPhotoClick: (index: Int) -> Unit
) {
    LazyRow(
        modifier = Modifier.alpha(contentAlpha),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(feeding.photos) { index, photo ->
            if (index == 0) WidthSpacer(width = 30.dp)
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(photo.url)
                    .diskCacheKey(photo.name)
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .size(66.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { onPhotoClick(index) }
            )
            if (index == feeding.photos.lastIndex) WidthSpacer(width = 30.dp)
        }
    }
}