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
    val photos: @RawValue ImmutableList<NetworkFile> = persistentListOf(),
    val reviewedBy: String? = null,
    val rejectionReason: String? = null,

    // id is not unique because it is equal to the feeding point id
    // TODO: Remove when EPMEDU-3783 is implemented
    val temporaryId: String = "",
) : Parcelable