package com.epmedu.animeal.feeding.domain.model

import java.util.Date

data class FeedingInProgress(
    val id: String,
    val name: String,
    val surname: String,
    val startDate: Date
)
