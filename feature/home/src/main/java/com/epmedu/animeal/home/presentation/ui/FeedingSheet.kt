package com.epmedu.animeal.home.presentation.ui

import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.epmedu.animeal.feeding.presentation.model.FeedingPhotoItem
import com.epmedu.animeal.feeding.presentation.model.FeedingPointModel
import com.epmedu.animeal.feeding.presentation.ui.FeedingPointSheetContent
import com.epmedu.animeal.feeding.presentation.ui.MarkFeedingDoneSheet
import com.epmedu.animeal.home.presentation.model.CameraState
import com.epmedu.animeal.router.presentation.FeedingRouteState

@Composable
fun FeedingSheet(
    feedingState: FeedingRouteState,
    feedingPoint: FeedingPointModel,
    feedingPhotos: List<FeedingPhotoItem>,
    cameraState: CameraState,
    contentAlpha: Float,
    onFavouriteChange: (Boolean) -> Unit,
    onDeletePhotoClick: (FeedingPhotoItem) -> Unit,
    onTakePhotoClick: () -> Unit,
) {
    when (feedingState) {
        is FeedingRouteState.Active -> {
            MarkFeedingDoneSheet(
                modifier = Modifier.wrapContentHeight(),
                photos = feedingPhotos,
                isUploadingNextImage = cameraState == CameraState.LoadingImageToServer,
                feedingPointTitle = feedingPoint.title,
                onDeletePhotoClick = onDeletePhotoClick,
                onTakePhotoClick = onTakePhotoClick
            )
        }
        else -> {
            FeedingPointSheetContent(
                modifier = Modifier.wrapContentHeight(),
                feedingPoint = feedingPoint,
                contentAlpha = contentAlpha,
                onFavouriteChange = onFavouriteChange,
            )
        }
    }
}