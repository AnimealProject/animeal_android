package com.epmedu.animeal.feeding.presentation.model

import android.text.format.DateUtils
import com.epmedu.animeal.common.constants.DefaultConstants.EMPTY_STRING
import com.epmedu.animeal.extensions.HOUR_IN_MILLIS
import com.epmedu.animeal.feeding.domain.model.FeedingHistory as DomainFeedingHistory
import com.epmedu.animeal.feeding.domain.model.FeedingInProgress as DomainFeedingInProgress

sealed class Feeding(
    open val id: String = EMPTY_STRING,
    open val feederName: String = EMPTY_STRING
) {
    data class InProgress(
        override val id: String = EMPTY_STRING,
        override val feederName: String = EMPTY_STRING,
        val timeLeft: Long = 0L
    ) : Feeding(id, feederName) {
        constructor(feedingInProgress: DomainFeedingInProgress) : this(
            id = feedingInProgress.id,
            feederName = "${feedingInProgress.name} ${feedingInProgress.surname}",
            timeLeft = HOUR_IN_MILLIS - (System.currentTimeMillis() - feedingInProgress.startDate.time)
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
