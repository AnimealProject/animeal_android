package com.epmedu.animeal.feeding.presentation.model

import android.text.format.DateUtils
import com.epmedu.animeal.common.constants.DefaultConstants.EMPTY_STRING
import com.epmedu.animeal.feeding.domain.model.Feeder

data class Feeder(
    val id: String = EMPTY_STRING,
    val name: String = EMPTY_STRING,
    val elapsedTime: String = EMPTY_STRING
) {
    constructor(feeder: Feeder) : this(
        id = feeder.id,
        name = "${feeder.name} ${feeder.surname}",
        elapsedTime = DateUtils.getRelativeTimeSpanString(
            feeder.feedingDate.time,
            System.currentTimeMillis(),
            DateUtils.SECOND_IN_MILLIS
        ).toString()
    )
}
