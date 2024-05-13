package com.epmedu.animeal.feedings.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import com.epmedu.animeal.feedings.presentation.model.FeedingModelStatus
import com.epmedu.animeal.foundation.loading.ShimmerLoading
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.spacer.HeightSpacer
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.networkstorage.domain.NetworkFile
import kotlinx.collections.immutable.toImmutableList

@Composable
internal fun FeedingItemSheetContent(
    feeding: FeedingModel,
    contentAlpha: Float,
    modifier: Modifier = Modifier
) {
    var currentPhotoIndex by rememberSaveable(feeding.id) { mutableStateOf(0) }
    val currentPhoto by remember(currentPhotoIndex, feeding.id) {
        mutableStateOf(feeding.photos[currentPhotoIndex])
    }
    var isPhotoLoading by rememberSaveable(feeding.id) { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        FeedingItemSheetHeading(feeding)
        HeightSpacer(height = 24.dp)
        Box(
            modifier = Modifier
                .weight(1f)
                .alpha(contentAlpha)
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 30.dp)
                .clip(RoundedCornerShape(8.dp))
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(currentPhoto.url)
                    .diskCacheKey(currentPhoto.name)
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.Fit,
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
        HeightSpacer(height = 10.dp)
        FeedingPhotoCarousel(
            feeding = feeding,
            contentAlpha = contentAlpha,
            onPhotoClick = { index -> currentPhotoIndex = index }
        )
    }
}

@AnimealPreview
@Composable
private fun FeedingItemSheetContentPreview() {
    val image = NetworkFile(
        name = "cat",
        url = "https://i.natgeofe.com/n/548467d8-c5f1-4551-9f58-6817a8d2c45e/NationalGeographic_2572187_square.jpg"
    )
    val secondImage = NetworkFile(
        name = "cat2",
        url = "https://images.pexels.com/photos/45201/kitty-cat-kitten-pet-45201.jpeg"
    )

    AnimealTheme {
        FeedingItemSheetContent(
            feeding = FeedingModel(
                id = "",
                title = "Feeding point",
                feeder = "Feeder name",
                status = FeedingModelStatus.PENDING_ORANGE,
                elapsedTime = "1 hour ago",
                image = image,
                photos = List(10) { index ->
                    when {
                        index % 2 == 0 -> image
                        else -> secondImage
                    }
                }.toImmutableList()
            ),
            contentAlpha = 0f
        )
    }
}