package com.epmedu.animeal.feedings.presentation.model

import android.os.Parcelable
import androidx.compose.runtime.Stable
import com.epmedu.animeal.networkstorage.domain.NetworkFile
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Stable
@Parcelize
data class FeedingModel(
    val id: String,
    val title: String,
    val feeder: String,
    val status: FeedingModelStatus,
    val elapsedTime: String,
    val image: NetworkFile? = null,
    val photos: @RawValue ImmutableList<NetworkFile> = persistentListOf()
) : Parcelable