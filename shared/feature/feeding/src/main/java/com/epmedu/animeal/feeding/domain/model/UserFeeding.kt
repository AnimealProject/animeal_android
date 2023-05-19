package com.epmedu.animeal.feeding.domain.model

import java.util.Date

data class UserFeeding(
    val feedingPoint: FeedingPoint,
    val createdAt: Date
)