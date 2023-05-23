package com.epmedu.animeal.feeding.presentation.model

import android.text.format.DateUtils
import com.epmedu.animeal.common.constants.DefaultConstants.EMPTY_STRING
import com.epmedu.animeal.feeding.domain.model.FeedingHistory

data class FeedingHistory(
    val id: String = EMPTY_STRING,
    val feederName: String = EMPTY_STRING,
    val elapsedTime: String = EMPTY_STRING
) {
    constructor(feedingHistory: FeedingHistory) : this(
        id = feedingHistory.id,
        feederName = "${feedingHistory.name} ${feedingHistory.surname}",
        elapsedTime = DateUtils.getRelativeTimeSpanString(
            feedingHistory.date.time,
            System.currentTimeMillis(),
            DateUtils.SECOND_IN_MILLIS
        ).toString()
    )
}
