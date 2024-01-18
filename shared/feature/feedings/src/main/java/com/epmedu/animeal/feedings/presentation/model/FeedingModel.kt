package com.epmedu.animeal.feedings.presentation.model

import android.os.Parcelable
import android.text.format.DateUtils
import androidx.compose.runtime.Stable
import com.epmedu.animeal.feeding.domain.model.Feeding
import com.epmedu.animeal.feeding.domain.model.FeedingHistory
import com.epmedu.animeal.feeding.domain.model.FeedingPoint
import kotlinx.parcelize.Parcelize

@Stable
@Parcelize
data class FeedingModel(
    val id: String,
    val title: String,
    val user: String,
    val status: FeedingModelStatus,
    val image: String,
    val elapsedTime: String,
) : Parcelable