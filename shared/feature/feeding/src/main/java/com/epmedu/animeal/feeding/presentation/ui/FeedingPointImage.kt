package com.epmedu.animeal.feeding.presentation.ui

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.epmedu.animeal.networkstorage.domain.NetworkFile

@Composable
fun FeedingPointImage(
    image: NetworkFile?,
    contentDescription: String,
    size: Dp = 80.dp
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(image?.url)
            .diskCacheKey(image?.name)
            .crossfade(true)
            .build(),
        contentScale = ContentScale.Crop,
        contentDescription = contentDescription,
        modifier = Modifier
            .size(size)
            .clip(RoundedCornerShape(16.dp))
    )
}