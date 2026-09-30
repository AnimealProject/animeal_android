package com.epmedu.animeal.messages.presentation.model

import android.os.Parcelable
import androidx.compose.runtime.Stable
import com.epmedu.animeal.networkstorage.domain.NetworkFile
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Stable
@Parcelize
data class MessageModel(
    val id: String,
    val feedingPointId: String,
    val content: String,
    val sender: String,
    val photos: @RawValue ImmutableList<NetworkFile> = persistentListOf(),
    val isNew: Boolean
) : Parcelable