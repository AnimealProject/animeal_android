package com.epmedu.animeal.feeding.presentation.ui

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.epmedu.animeal.feeding.presentation.model.FeedingPhotoItem
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.theme.CustomColor
import com.epmedu.animeal.resources.R

@Composable
fun MarkFeedingDonePhotoGallery(
    photos: List<FeedingPhotoItem>,
    isUploadingNextImage: Boolean,
    isTakePhotoAvailable: Boolean,
    onTakePhotoClick: () -> Unit,
    onDeletePhotoClick: (FeedingPhotoItem) -> Unit,
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(items = photos) { item ->
            PhotoItem(
                uri = item.uri,
                onDeleteClick = {
                    onDeletePhotoClick(item)
                }
            )
        }

        item {
            if (isUploadingNextImage) {
                CircularProgressIndicator(Modifier.size(70.dp))
            } else if (isTakePhotoAvailable) {
                TakePhotoItem(onClick = onTakePhotoClick)
            }
        }
    }
}

@Composable
private fun TakePhotoItem(
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(70.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(CustomColor.LynxWhite)
            .clickable { onClick() },
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(R.drawable.ic_picture_plus),
            contentScale = ContentScale.FillWidth,
            contentDescription = null
        )
    }
}

@Composable
private fun PhotoItem(
    uri: Uri,
    onDeleteClick: () -> Unit
) {
    val iconSize = 24.dp
    val offsetInPx = LocalDensity.current.run { (iconSize / 3).roundToPx() }

    Box(
        modifier = Modifier
            .size(70.dp)
            .background(
                color = CustomColor.LynxWhite,
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        AsyncImage(
            modifier = Modifier.clip(RoundedCornerShape(16.dp)),
            model = uri,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        IconButton(
            onClick = onDeleteClick,
            modifier = Modifier
                .offset { IntOffset(x = +offsetInPx, y = -offsetInPx) }
                .border(width = 1.dp, color = Color.White, shape = CircleShape)
                .clip(CircleShape)
                .background(CustomColor.DarkerGrey)
                .size(iconSize)
                .align(Alignment.TopEnd)
        ) {
            Icon(
                imageVector = Icons.Rounded.Close,
                tint = Color.White,
                contentDescription = null,
            )
        }
    }
}

@Composable
@AnimealPreview
private fun MarkFeedingDonePhotoGalleryPreview() {
    AnimealTheme {
        MarkFeedingDonePhotoGallery(
            photos = listOf(FeedingPhotoItem.empty, FeedingPhotoItem.empty),
            isUploadingNextImage = false,
            isTakePhotoAvailable = true,
            onTakePhotoClick = {},
            onDeletePhotoClick = {}
        )
    }
}