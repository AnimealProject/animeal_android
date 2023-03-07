package com.epmedu.animeal.home.presentation.ui

import android.net.Uri
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.epmedu.animeal.feeding.presentation.model.FeedingPointModel
import com.epmedu.animeal.feeding.presentation.ui.FeedingPointSheetContent
import com.epmedu.animeal.feeding.presentation.ui.MarkFeedingDoneSheet
import com.epmedu.animeal.home.presentation.model.FeedingRouteState

@Suppress("LongParameterList")
@Composable
fun FeedingSheet(
    modifier: Modifier = Modifier,
    feedingState: FeedingRouteState,
    feedingPoint: FeedingPointModel,
    contentAlpha: Float,
    onFavouriteChange: (Boolean) -> Unit,
    onDeletePhotoClick: (Uri) -> Unit,
    onTakePhotoClick: () -> Unit,
) {
    when (feedingState) {
        is FeedingRouteState.Active -> {
            MarkFeedingDoneSheet(
                modifier = modifier.wrapContentHeight(),
                feedingPointTitle = feedingPoint.title,
                onDeletePhotoClick = onDeletePhotoClick,
                onTakePhotoClick = onTakePhotoClick
            )
        }
        else -> {
            FeedingPointSheetContent(
                modifier = modifier.wrapContentHeight(),
                feedingPoint = feedingPoint,
                contentAlpha = contentAlpha,
                onFavouriteChange = onFavouriteChange
            )
        }
    }
}