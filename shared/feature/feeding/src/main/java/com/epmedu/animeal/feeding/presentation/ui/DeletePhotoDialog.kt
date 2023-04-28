package com.epmedu.animeal.feeding.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.epmedu.animeal.feeding.presentation.model.FeedingPhotoItem
import com.epmedu.animeal.foundation.dialog.AnimealQuestionDialog
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.epmedu.animeal.resources.R

@Composable
fun DeletePhotoDialog(
    photoItem: FeedingPhotoItem,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    val height = LocalConfiguration.current.screenHeightDp / 3
    AnimealQuestionDialog(
        title = stringResource(id = R.string.are_you_sure_you_want_delete_photo),
        dismissText = stringResource(id = R.string.no),
        acceptText = stringResource(id = R.string.yes),
        onDismiss = onDismiss,
        onConfirm = onConfirm,
        content = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .height(height.dp),
                    model = photoItem.uri,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
        }
    )
}

@AnimealPreview
@Composable
fun DeletePhotoDialogPreview() {
    AnimealTheme {
        DeletePhotoDialog(
            FeedingPhotoItem.empty,
            {},
            {}
        )
    }
}