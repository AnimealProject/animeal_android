package com.epmedu.animeal.feeding.domain.model

import java.util.Date

data class FeedingHistory(
    val id: String,
    val name: String,
    val surname: String,
    val date: Date
)
