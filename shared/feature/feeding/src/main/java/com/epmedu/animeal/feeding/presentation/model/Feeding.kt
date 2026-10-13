package com.epmedu.animeal.feeding.presentation.model

import android.text.format.DateUtils
import com.epmedu.animeal.common.constants.DefaultConstants.EMPTY_STRING
import com.epmedu.animeal.feeding.domain.model.FeedingHistory as DomainFeedingHistory
import com.epmedu.animeal.feeding.domain.model.FeedingInProgress as DomainFeedingInProgress

sealed class Feeding(
    open val id: String = EMPTY_STRING,
    open val feederName: String = EMPTY_STRING
) {
    data class InProgress(
        override val id: String = EMPTY_STRING,
        override val feederName: String = EMPTY_STRING,
        val startTime: Long = 0L,
        val endTime: Long? = null
    ) : Feeding(id, feederName) {
        constructor(feedingInProgress: DomainFeedingInProgress) : this(
            id = feedingInProgress.id,
            feederName = "${feedingInProgress.name} ${feedingInProgress.surname}",
            startTime = feedingInProgress.startDate.time,
            endTime = feedingInProgress.endDate?.time
        )
    }

    data class History(
        override val id: String = EMPTY_STRING,
        override val feederName: String = EMPTY_STRING,
        val elapsedTime: String = EMPTY_STRING
    ) : Feeding(id, feederName) {
        constructor(feedingHistory: DomainFeedingHistory) : this(
            id = feedingHistory.id,
            feederName = "${feedingHistory.name} ${feedingHistory.surname}",
            elapsedTime = DateUtils.getRelativeTimeSpanString(
                feedingHistory.date.time,
                System.currentTimeMillis(),
                DateUtils.SECOND_IN_MILLIS
            ).toString()
        )
    }
}
