package com.epmedu.animeal.feeding.domain.model

import com.epmedu.animeal.users.domain.model.User
import java.util.Date

data class Feeding(
    val id: String,
    val feeder: User?,
    val status: FeedingStatus,
    val date: Date,
    val feedingPointId: String
)
