package com.epmedu.animeal.feedings.presentation.model

import android.os.Parcelable
import android.text.format.DateUtils
import androidx.compose.runtime.Stable
import com.epmedu.animeal.feeding.domain.model.FeedingHistory
import com.epmedu.animeal.feeding.domain.model.FeedingInProgress
import com.epmedu.animeal.feeding.domain.model.FeedingPoint
import kotlinx.parcelize.Parcelize

@Stable
@Parcelize
data class FeedingItem(
    val id: String,
    val title: String,
    val user: String,
    val status: FeedStatus,
    val image: String,
    val elapsedTime: String,
) : Parcelable

fun FeedingInProgress.toFeedingItem(feedingPoint: FeedingPoint) =
    FeedingItem(
        id = feedingPoint.id,
        title = feedingPoint.title,
        user = "$name $surname",
        status = FeedStatus.IN_PROGRESS,
        image = feedingPoint.images[0],
        elapsedTime = DateUtils.getRelativeTimeSpanString(
            startDate.time,
            System.currentTimeMillis(),
            DateUtils.SECOND_IN_MILLIS
        ).toString()
    )

fun FeedingHistory.toFeedingItem(feedingPoint: FeedingPoint) =
    FeedingItem(
        id = feedingPoint.id,
        title = feedingPoint.title,
        user = "$name $surname",
        status = status.toFeedStatus(System.currentTimeMillis() - date.time),
        image = feedingPoint.images[0],
        elapsedTime = DateUtils.getRelativeTimeSpanString(
            date.time,
            System.currentTimeMillis(),
            DateUtils.SECOND_IN_MILLIS
        ).toString()
    )