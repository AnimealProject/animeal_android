package com.epmedu.animeal.feedings.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter.State.Loading
import coil.request.ImageRequest
import com.epmedu.animeal.feedings.presentation.model.FeedingModel
import com.epmedu.animeal.foundation.loading.ShimmerLoading
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
            var isPhotoLoading by rememberSaveable(feeding.id) { mutableStateOf(false) }

            if (index == 0) WidthSpacer(width = 30.dp)

            Box(
                modifier = Modifier
                    .size(66.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { onPhotoClick(index) }
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(photo.url)
                        .diskCacheKey(photo.name)
                        .crossfade(true)
                        .build(),
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    onState = { state ->
                        isPhotoLoading = state is Loading
                    }
                )
                if (isPhotoLoading) {
                    ShimmerLoading(
                        modifier = Modifier.fillMaxSize(),
                        shape = RoundedCornerShape(8.dp)
                    )
                }
            }
            if (index == feeding.photos.lastIndex) WidthSpacer(width = 30.dp)
        }
    }
}