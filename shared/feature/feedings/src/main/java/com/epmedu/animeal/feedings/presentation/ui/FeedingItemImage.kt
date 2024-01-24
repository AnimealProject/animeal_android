package com.epmedu.animeal.feedings.presentation.ui

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.epmedu.animeal.networkstorage.domain.NetworkFile

@Composable
internal fun FeedingItemImage(image: NetworkFile?) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(image?.url)
            .diskCacheKey(image?.name)
            .crossfade(true)
            .build(),
        contentScale = ContentScale.Crop,
        contentDescription = null,
        modifier = Modifier
            .size(80.dp)
            .clip(RoundedCornerShape(16.dp))
    )
}