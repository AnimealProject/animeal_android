package com.epmedu.animeal.feeding.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import com.epmedu.animeal.feeding.presentation.model.FeedingPhotoItem
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.foundation.theme.CustomColor
import com.epmedu.animeal.networkstorage.domain.NetworkFile
import com.epmedu.animeal.resources.R

@Composable
fun MarkFeedingDoneSheet(
    code: String,
    title: String,
    image: NetworkFile?,
    onTakePhotoClick: () -> Unit,
    onDeletePhotoClick: (FeedingPhotoItem) -> Unit,
    modifier: Modifier = Modifier,
    photos: List<FeedingPhotoItem> = emptyList(),
    isUploadingNextImage: Boolean = false,
    isTakePhotoAvailable: Boolean = true
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.surface)
            .padding(
                top = 8.dp,
                bottom = 110.dp,
                start = 24.dp,
                end = 24.dp,
            ),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Divider(
            modifier = Modifier
                .width(18.dp)
                .clip(RoundedCornerShape(2.dp)),
            thickness = 4.dp,
        )

        MarkFeedingDoneHeader(
            code = code,
            title = title,
            image = image
        )

        Divider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
        )

        MarkFeedingDoneContent(
            photos = photos,
            isUploadingNextImage = isUploadingNextImage,
            isTakePhotoAvailable = isTakePhotoAvailable,
            onTakePhotoClick = onTakePhotoClick,
            onDeletePhotoClick = onDeletePhotoClick
        )
    }
}

@Composable
private fun MarkFeedingDoneHeader(
    code: String,
    title: String,
    image: NetworkFile?
) {
    Row(
        modifier = Modifier
            .heightIn(min = 60.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        FeedingPointImage(
            size = 60.dp,
            image = image,
            contentDescription = title
        )
        Column(
            modifier = Modifier.weight(1f)
        ) {
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
    }
}

@Composable
private fun MarkFeedingDoneContent(
    photos: List<FeedingPhotoItem>,
    isUploadingNextImage: Boolean,
    isTakePhotoAvailable: Boolean,
    onTakePhotoClick: () -> Unit,
    onDeletePhotoClick: (FeedingPhotoItem) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.feeding_take_pictures),
            style = MaterialTheme.typography.caption,
            overflow = TextOverflow.Ellipsis,
            color = CustomColor.DarkerGrey,
        )
        MarkFeedingDonePhotoGallery(
            photos = photos,
            isUploadingNextImage = isUploadingNextImage,
            isTakePhotoAvailable = isTakePhotoAvailable,
            onTakePhotoClick = onTakePhotoClick,
            onDeletePhotoClick = onDeletePhotoClick
        )
    }
}

@AnimealPreview
@Composable
private fun MarkFeedingDoneSheetPreview(@PreviewParameter(LoremIpsum::class) text: String) {
    AnimealTheme {
        MarkFeedingDoneSheet(
            modifier = Modifier.fillMaxHeight(),
            code = "GE-TB-0000-D-0000",
            title = text.take(50),
            image = null,
            onDeletePhotoClick = {},
            onTakePhotoClick = {}
        )
    }
}